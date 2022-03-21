package com.sliide.data.rest

import com.sliide.data.users.UserService

interface ServicesProvider {

    fun provideUsersService(): UserService
}