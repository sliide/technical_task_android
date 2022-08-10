package com.sliide.demoapp.repository

import com.sliide.demoapp.model.User
import com.sliide.demoapp.retrofit.UserNetworkMapper
import com.sliide.demoapp.retrofit.UserService
import com.sliide.demoapp.room.UserCacheMapper
import com.sliide.demoapp.room.UserDao
import com.sliide.demoapp.utils.mapper.ResponseCodeMapper
import com.sliide.demoapp.utils.response.DataState
import java.lang.Exception
import java.net.UnknownHostException

/**
 *  Class responsible for implementing [MainRepository] functionalities
 */
class MainRepositoryImpl
constructor(
    private val userService: UserService,
    private val userDao: UserDao,
    private val networkMapper: UserNetworkMapper,
    private val cacheMapper: UserCacheMapper,
) : MainRepository {

    /**
     *  Method responsible for fetching and storing users
     *  @return a [DataState.Success] with users list in case of a success
     *  @return a [DataState.Error] with error message in case of an error
     */
    override suspend fun getUsers(): DataState<List<User>> {

        //Fetch last page from api
        val lastPage = getLastPage()

        //Check if last page was fetch with success
        if (lastPage != -1) {
            // fetch users from last page
            val usersResponse = userService.getUsers(lastPage)
            return if (usersResponse.isSuccessful) {
                // Get users from response body as a list of [UserNetworkEntity]
                val networkUsers = userService.getUsers(lastPage).body() ?: emptyList()
                // Convert network entities to domain models
                val users = networkMapper.mapFromEntitiesList(networkUsers)

                //TODO: Improve using @Transaction

                // Delete all users that are not currently on the last page (side-effect lost of added
                // user from app since they not added to the last page)
                userDao.deleteIfNotInList(users.map { it.id })
                for (user in users) {
                    // Insert users in database and don't update users that are already cached
                    // in order to keep their relative time.
                    userDao.insert(cacheMapper.mapFromDomainModel(user))
                }

                // Get cached users
                val cacheUsers = userDao.getUsers()

                // Return success data state with contains mapped and ordered users list to domain model
                return DataState.Success(cacheMapper.mapFromEntitiesList(cacheUsers))
            } else {
                DataState.Error(ResponseCodeMapper.map(usersResponse.code()))
            }
        } else {
            //TODO: Handle no internet connection case
            return DataState.Error("Something went wrong!")
        }
    }

    /**
     *  Method responsible for adding and storing users
     *  @return a [DataState.Success] with user model in case of a success
     *  @return a [DataState.Error] with error message in case of an error
     */
    override suspend fun addUser(user: User): DataState<User> {
        return try {
            // Post api call in order to add a new user.
            val addUserResponse = userService.addUser(networkMapper.mapFromDomainModel(user))
            return if (addUserResponse.isSuccessful && addUserResponse.code() == 201) {

                //TODO: Improve using @Transaction and combine insert with get

                // In case of a success store user into database on order to have relative time
                // reference
                userDao.insert(cacheMapper.mapFromDomainModel(user))

                //return success DataState with user instance fetch from local db
                DataState.Success(cacheMapper.mapFromEntity(userDao.getUser(user.id)))
            } else {
                DataState.Error(ResponseCodeMapper.map(addUserResponse.code()))
            }
        } catch (e: Exception) {
            if (e is UnknownHostException) {
                // Handle internet connection exception
                DataState.Error("No internet connection!")
            } else {
                // Generic case for other possible errors
                DataState.Error("Something went wrong!")
            }
        }
    }

    //TODO: Can be improve similar to all api interaction, with no cache
    suspend fun getLastPage(): Int {
        try {
            userService.getPagination().headers()["X-Pagination-Pages"]?.let {
                return it.toInt()
            }
            return -1
        } catch (exception: Exception) {
            return -1
        }
    }

    /**
     *  Method responsible for deleting an User
     *  @return a [DataState.Success] with deleted user id
     *  @return a [DataState.Error] with error message in case of an error
     */
    override suspend fun deleteUser(userId: Int): DataState<Int> {
        return try {
            val deleteUserResponse = userService.deleteUser(userId)
            return if (deleteUserResponse.isSuccessful && deleteUserResponse.code() == 204) {
                // Returns success in case of a successful api call and don't delete from local database
                // because local deletion will occur when fetching a new list
                // Deletion will be needed if cache fallback will be enabled
                DataState.Success(userId)
            } else {
                DataState.Error(ResponseCodeMapper.map(deleteUserResponse.code()))
            }
        } catch (e: Exception) {
            if (e is UnknownHostException) {
                DataState.Error("No internet connection!")
            } else {
                DataState.Error("Something went wrong!")
            }
        }
    }
}