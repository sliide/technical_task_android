package com.hanna.sliidetest.data.repositories.repositories

import com.hanna.sliidetest.data.dto.MetaDto
import com.hanna.sliidetest.data.network.Resource
import com.hanna.sliidetest.models.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    suspend fun getMetaData(): Resource<MetaDto>
    suspend fun getUsers(page: Int): Flow<Resource<List<User>>>
}