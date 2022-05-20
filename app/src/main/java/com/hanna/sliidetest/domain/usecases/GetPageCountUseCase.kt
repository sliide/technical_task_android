package com.hanna.sliidetest.domain.usecases

import com.hanna.sliidetest.data.repositories.repositories.UsersRepository
import javax.inject.Inject

class GetPageCountUseCase @Inject constructor(private val repository: UsersRepository) {

    suspend operator fun invoke(): Int {
        return repository.getPageCount()
    }
}