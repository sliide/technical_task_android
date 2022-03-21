package com.sliide.data.users

import com.sliide.boundary.users.LoadPageFailed
import com.sliide.boundary.users.UsersRepo
import com.sliide.data.rest.ServicesProvider
import com.sliide.data.retrofit.asSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class UsersRepoTest {

    @Test
    fun `request users list EXPECT the same list size`() {
        val page = 1
        val successResult = ResultsFactory.successUsers(page = page, pageSize = 10)

        val userService = mock<UserService> {
            on { runBlocking { users(page = page) } } doReturn successResult
        }

        val servicesProvider = mock<ServicesProvider> {
            on { provideUsersService() } doReturn userService
        }

        val repo: UsersRepo = UsersRepoImpl(servicesProvider, Dispatchers.IO)

        runBlocking {
            val users = repo.users(1)
            assert(users.size == successResult.asSuccess().value.size)
        }
    }

    @Test(expected = LoadPageFailed::class)
    fun `request users list EXPECT exception`() {
        val page = 1
        val failureResult = ResultsFactory.failureUsers()

        val userService = mock<UserService> {
            on { runBlocking { users(page = page) } } doReturn failureResult
        }

        val servicesProvider = mock<ServicesProvider> {
            on { provideUsersService() } doReturn userService
        }

        val repo: UsersRepo = UsersRepoImpl(servicesProvider, Dispatchers.IO)

        runBlocking {
            repo.users(1)
        }
    }
}