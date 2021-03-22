package com.devforfun.sliidetask.repository

import com.devforfun.sliidetask.services.UsersService
import com.devforfun.sliidetask.services.events.Result
import com.devforfun.sliidetask.services.model.User
import io.reactivex.Single


class UsersRepository(val usersService: UsersService) : BaseRepository() {

    fun getUsers(): Single<Result<List<User>>> = usersService.fetchUsers()

    fun deleteUser(userId: Int): Single<Result<Int>> = usersService.deleteUser(userId)

    fun createUser(user : User) : Single<Result<User>> = usersService.createUser(user)
}

