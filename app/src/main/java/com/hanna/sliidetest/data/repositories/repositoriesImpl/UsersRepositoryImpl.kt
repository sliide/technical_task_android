package com.hanna.sliidetest.data.repositories.repositoriesImpl

import com.hanna.sliidetest.data.api.UsersApi
import com.hanna.sliidetest.data.db.UsersDao
import com.hanna.sliidetest.data.dto.UserDto
import com.hanna.sliidetest.data.mappers.UserMapper
import com.hanna.sliidetest.data.network.*
import com.hanna.sliidetest.data.repositories.repositories.UsersRepository
import com.hanna.sliidetest.models.User
import com.hanna.sliidetest.models.UserStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(val api: UsersApi, val dao: UsersDao) :
    UsersRepository {

    //any random request to get the header response
    override suspend fun getPageCount(): Int {
        val meta = withContext(Dispatchers.IO) {
            api.getMeta()
        }.last()
        return when (meta) {
            is ApiEmptyResponse -> 1
            is ApiErrorResponse -> 1
            is ApiSuccessResponse -> {
                meta.headers?.get("X-Pagination-Pages")?.toInt() ?: 1
            }
        }
    }

    override suspend fun getUsers(page: Int): Flow<Resource<List<User>>> {
        return object : FlowNetworkBoundResource<List<User>, List<UserDto>>() {
            override suspend fun saveNetworkResult(item: List<UserDto>) {
//                if wed want to update and have only the last page, (and not cached users) users table should be deleted here
                dao.insertOrUpdate(item.map { UserMapper.map(it) })
            }

            override fun shouldFetch(): Boolean {
                //would add condition to determine if update from network request is required.
                return true
            }

            override suspend fun loadFromDb(): Flow<List<User>> {
                return dao.getAllUsers()
            }

            override suspend fun fetchFromNetwork(): Flow<ApiResponse<List<UserDto>>> {
                return api.getUsers(page)
            }
        }.asFlow()
    }

    override suspend fun addUser(user: User): Flow<Resource<User>> {
        val savedUserResponse = withContext(Dispatchers.IO) {
            val userDto = UserDto(
                null,
                user.name,
                user.email,
                user.gender.gender,
                UserStatus.ACTIVE.statusValue
            )//based on the assumption all newly created users are active
            api.addUser(userDto)
        }.last()
        return flow {
            val resource = when (savedUserResponse) {
                is ApiEmptyResponse -> Resource.success(null)
                is ApiErrorResponse -> Resource.error(savedUserResponse.errorMessage, null)
                is ApiSuccessResponse -> {
                    val userResponse = UserMapper.map(savedUserResponse.body)
                    dao.insertUser(userResponse)
                    Resource.success(userResponse)
                }
            }
            emit(resource)
        }
    }
}