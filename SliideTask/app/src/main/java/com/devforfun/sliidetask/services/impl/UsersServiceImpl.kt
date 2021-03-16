package com.devforfun.sliidetask.services.impl;


import com.devforfun.sliidetask.exceptions.InternalServerErrorException
import com.devforfun.sliidetask.repository.Result
import com.devforfun.sliidetask.services.UsersProvider
import com.devforfun.sliidetask.services.UsersService
import com.devforfun.sliidetask.services.model.User
import com.devforfun.sliidetask.services.model.UserBody
import io.reactivex.Single

class UsersServiceImpl(private val provider: UsersProvider) : UsersService {

    override fun fetchUsers(): Single<Result<List<User>>> =
        provider.getUsers()
            .flatMap { provider.getUsers(it.meta.pagination.pages) }
            .map { userResponse ->
                if (userResponse.code == 200) {
                    Result.Success(userResponse.data)
                } else {
                    Result.Error(Exception())
                }
            }.doOnSuccess { result -> Single.just(result) }
            .doOnError { handleException(it) }

    override fun createUser(user: User): Single<Result<User>> =
        provider.createUser(UserBody(user.name, user.gender, user.email, user.status))
            .map { createUserResponse ->
                if (createUserResponse.code == 201) {
                    Result.Success(user)
                } else {
                    Result.Error(Exception())
                }
            }.doOnSuccess { result -> Single.just(result) }
            .doOnError { throwable -> handleException(throwable) }

    override fun deleteUser(userId: Int): Single<Result<Int>> =
        provider.deleteUser(userId)
            .map { deleteUserResponse ->
                if (deleteUserResponse.code == 204) {
                    Result.Success(userId)
                } else {
                    Result.Error(Exception())
                }
            }.doOnSuccess { result -> Single.just(result) }
            .doOnError { throwable -> handleException(throwable) }


    private fun handleException(throwable: Throwable?): Result.Error {
        //todo handle exceptions for each error code
        return when (throwable) {
            is InternalServerErrorException -> Result.Error(Exception("Internal Server Exception"))
            else -> Result.Error(Exception("Error fetching data"))
        }
    }
}