package com.hanna.sliidetest.data.repositories.repositoriesImpl

import android.util.Log
import com.hanna.sliidetest.data.api.UsersApi
import com.hanna.sliidetest.data.db.UsersDao
import com.hanna.sliidetest.data.dto.MetaDto
import com.hanna.sliidetest.data.dto.UsersResponse
import com.hanna.sliidetest.data.mappers.UserMapper
import com.hanna.sliidetest.data.network.*
import com.hanna.sliidetest.data.repositories.repositories.UsersRepository
import com.hanna.sliidetest.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(val api: UsersApi, val dao: UsersDao) :
    UsersRepository {

    override suspend fun getMetaData(): Resource<MetaDto> {
        val meta = withContext(Dispatchers.IO) {
            api.getMeta()
        }.last()
        return when (meta) {
            is ApiEmptyResponse -> Resource.success(null)
            is ApiErrorResponse -> Resource.error(meta.errorMessage, null)
            is ApiSuccessResponse -> Resource.success(meta.body.meta)
        }
    }

    override suspend fun getUsers(page: Int): Flow<Resource<List<User>>> {
        return object : FlowNetworkBoundResource<List<User>, UsersResponse>() {
            override suspend fun saveNetworkResult(item: UsersResponse) {
//                if wed want to update and have only the last page, (and not cached users) users table should be deleted here
                dao.insertOrUpdate(item.data.map { UserMapper.map(it) })
            }

            override fun shouldFetch(): Boolean {
                //would add condition to determine if update from network request is required.
                return true
            }

            override suspend fun loadFromDb(): Flow<List<User>> {
                return dao.getAllUsers()
            }

            override suspend fun fetchFromNetwork(): Flow<ApiResponse<UsersResponse>> {
                return api.getUsers(page)
            }
        }.asFlow()
    }
}