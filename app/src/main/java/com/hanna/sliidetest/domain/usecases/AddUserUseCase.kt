package com.hanna.sliidetest.domain.usecases

import com.hanna.sliidetest.data.dto.MetaDto
import com.hanna.sliidetest.data.network.Resource
import com.hanna.sliidetest.data.repositories.repositories.UsersRepository
import com.hanna.sliidetest.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMetadataUseCase @Inject constructor(private val repository: UsersRepository) {

    suspend operator fun invoke(): Resource<MetaDto> {
        return repository.getMetaData()
    }
}