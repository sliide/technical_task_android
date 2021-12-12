package com.sa.gorestuserstask.domain.usecase

interface UseCase<I, O> {
    suspend fun invoke(input: I): Output<O>
}
