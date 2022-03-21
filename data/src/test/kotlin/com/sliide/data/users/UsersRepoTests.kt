package com.sliide.data.users

import com.sliide.boundary.users.*
import com.sliide.data.rest.ServicesProvider
import com.sliide.data.retrofit.asSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class UsersRepoTests {

    private fun createRepo(
        services: ServicesProvider,
        io: CoroutineDispatcher
    ): UsersRepo = UsersRepoImpl(services, io)

    @Test
    fun `request users list EXPECT the same list size`() {
        val page = 1
        val result = ResultsFactory.successUsers(page = page, pageSize = 10)

        val userService = mock<UserService> {
            on { runBlocking { users(page = page) } } doReturn result
        }

        val servicesProvider = mock<ServicesProvider> {
            on { provideUsersService() } doReturn userService
        }

        val repo = createRepo(servicesProvider, Dispatchers.IO)

        runBlocking {
            val users = repo.users(1)
            assert(users.size == result.asSuccess().value.size)
        }
    }

    @Test(expected = LoadPageFailed::class)
    fun `request users list EXPECT LoadPageFailed`() {
        val page = 1
        val result = ResultsFactory.failureUsers()

        val userService = mock<UserService> {
            on { runBlocking { users(page = page) } } doReturn result
        }

        val servicesProvider = mock<ServicesProvider> {
            on { provideUsersService() } doReturn userService
        }

        val repo = createRepo(servicesProvider, Dispatchers.IO)

        runBlocking {
            repo.users(1)
        }
    }

    @Test
    fun `request users list EXPECT LoadPageFailed with the same page`() {
        val page = 1
        val result = ResultsFactory.failureUsers()

        val userService = mock<UserService> {
            on { runBlocking { users(page = page) } } doReturn result
        }

        val servicesProvider = mock<ServicesProvider> {
            on { provideUsersService() } doReturn userService
        }

        val repo = createRepo(servicesProvider, Dispatchers.IO)

        runBlocking {
            try {
                repo.users(1)
            } catch (ex: LoadPageFailed) {
                assert(ex.page == page)
            }
        }
    }

    @Test
    fun `create user EXPECT user with the same name`() {
        val name = "Konstanty Kalinowski"
        val email = "konstanty.kalinowski@gmail.com"
        val successResult = ResultsFactory.successCreate(name, email)

        val create = CreateUserDto(name, email, "male", "active")

        val userService = mock<UserService> {
            on { runBlocking { create(body = create) } } doReturn successResult
        }

        val servicesProvider = mock<ServicesProvider> {
            on { provideUsersService() } doReturn userService
        }

        val repo = createRepo(servicesProvider, Dispatchers.IO)

        runBlocking {
            assert(repo.create(name, email).name == name)
        }
    }

    @Test
    fun `create user EXPECT user with the same email`() {
        val name = "Konstanty Kalinowski"
        val email = "konstanty.kalinowski@gmail.com"
        val successResult = ResultsFactory.successCreate(name, email)

        val create = CreateUserDto(name, email, "male", "active")

        val userService = mock<UserService> {
            on { runBlocking { create(body = create) } } doReturn successResult
        }

        val servicesProvider = mock<ServicesProvider> {
            on { provideUsersService() } doReturn userService
        }

        val repo = createRepo(servicesProvider, Dispatchers.IO)

        runBlocking {
            assert(repo.create(name, email).email == email)
        }
    }

    @Test(expected = FieldsProblem::class)
    fun `create user with email that has already taken EXPECT FieldsProblem`() {
        val name = "Konstanty Kalinowski"
        val email = "konstanty.kalinowski@gmail.com"
        val result = ResultsFactory.emailHasAlreadyTaken()

        val create = CreateUserDto(name, email, "male", "active")

        val userService = mock<UserService> {
            on { runBlocking { create(body = create) } } doReturn result
        }

        val servicesProvider = mock<ServicesProvider> {
            on { provideUsersService() } doReturn userService
        }

        val repo = createRepo(servicesProvider, Dispatchers.IO)

        runBlocking {
            repo.create(name, email)
        }
    }

    @Test
    fun `create user with email that has already taken EXPECT FieldsProblem with the email field`() {
        val name = "Konstanty Kalinowski"
        val email = "konstanty.kalinowski@gmail.com"
        val result = ResultsFactory.emailHasAlreadyTaken()

        val create = CreateUserDto(name, email, "male", "active")

        val userService = mock<UserService> {
            on { runBlocking { create(body = create) } } doReturn result
        }

        val servicesProvider = mock<ServicesProvider> {
            on { provideUsersService() } doReturn userService
        }

        val repo = createRepo(servicesProvider, Dispatchers.IO)

        runBlocking {
            try {
                repo.create(name, email)
            } catch (ex: FieldsProblem) {
                assert(ex.fields.containsKey("email"))
            }
        }
    }

    @Test
    fun `create user with email that has already taken EXPECT FieldsProblem with the correct message`() {
        val name = "Konstanty Kalinowski"
        val email = "konstanty.kalinowski@gmail.com"
        val result = ResultsFactory.emailHasAlreadyTaken()

        val create = CreateUserDto(name, email, "male", "active")

        val userService = mock<UserService> {
            on { runBlocking { create(body = create) } } doReturn result
        }

        val servicesProvider = mock<ServicesProvider> {
            on { provideUsersService() } doReturn userService
        }

        val repo = createRepo(servicesProvider, Dispatchers.IO)

        runBlocking {
            try {
                repo.create(name, email)
            } catch (ex: FieldsProblem) {
                assert(ex.fields["email"] == "has already been taken")
            }
        }
    }

    @Test(expected = CreateUserFailed::class)
    fun `create user with failed response EXPECT CreateUserFailed`() {
        val name = "Konstanty Kalinowski"
        val email = "konstanty.kalinowski@gmail.com"
        val result = ResultsFactory.creationUnauthorizedResponse()

        val create = CreateUserDto(name, email, "male", "active")

        val userService = mock<UserService> {
            on { runBlocking { create(body = create) } } doReturn result
        }

        val servicesProvider = mock<ServicesProvider> {
            on { provideUsersService() } doReturn userService
        }

        val repo = createRepo(servicesProvider, Dispatchers.IO)

        runBlocking {
            repo.create(name, email)
        }
    }

    @Test(expected = DeleteUserFailed::class)
    fun `delete user with failed response EXPECT DeleteUserFailed`() {
        val userId = 1
        val result = ResultsFactory.deleteUnauthorizedResponse()

        val userService = mock<UserService> {
            on { runBlocking { delete(userId) } } doReturn result
        }

        val servicesProvider = mock<ServicesProvider> {
            on { provideUsersService() } doReturn userService
        }

        val repo = createRepo(servicesProvider, Dispatchers.IO)

        runBlocking {
            repo.delete(1)
        }
    }
}