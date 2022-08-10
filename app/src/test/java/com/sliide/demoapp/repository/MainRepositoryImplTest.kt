package com.sliide.demoapp.repository

import com.google.common.truth.Truth
import com.sliide.demoapp.model.Gender
import com.sliide.demoapp.model.Status
import com.sliide.demoapp.model.User
import com.sliide.demoapp.retrofit.UserNetworkEntity
import com.sliide.demoapp.retrofit.UserNetworkMapper
import com.sliide.demoapp.retrofit.UserService
import com.sliide.demoapp.room.UserCacheEntity
import com.sliide.demoapp.room.UserCacheMapper
import com.sliide.demoapp.room.UserDao
import com.sliide.demoapp.utils.response.DataState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before

import org.junit.Test
import java.lang.Exception
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class MainRepositoryImplTest {

    @RelaxedMockK
    lateinit var mockUserService: UserService

    @RelaxedMockK
    lateinit var mockUserDao: UserDao

    @RelaxedMockK
    lateinit var mockUserNetworkMapper: UserNetworkMapper

    @RelaxedMockK
    lateinit var mockUserCacheMapper: UserCacheMapper

    @RelaxedMockK
    lateinit var mockUser: User

    @RelaxedMockK
    lateinit var mockResponse: retrofit2.Response<Unit>

    @RelaxedMockK
    lateinit var mockGetUserResponseResponse: retrofit2.Response<List<UserNetworkEntity>>

    @RelaxedMockK
    lateinit var mockCacheEntity: UserCacheEntity

    lateinit var mainRepositoryImpl: MainRepositoryImpl

    private val mockUserNetworkEntityList = arrayListOf(
        UserNetworkEntity(1, "name1", "name1@mail.com", Gender.MALE.genderValue, Status.ACTIVE.statusValue),
        UserNetworkEntity(2, "name2", "name2@mail.com", Gender.MALE.genderValue, Status.ACTIVE.statusValue),
        UserNetworkEntity(3, "name3", "name3@mail.com", Gender.MALE.genderValue, Status.ACTIVE.statusValue),
        UserNetworkEntity(5, "name4", "name4@mail.com", Gender.MALE.genderValue, Status.ACTIVE.statusValue)
    )

    private val mockUserCacheEntityList = arrayListOf(
        UserCacheEntity(1, "name1", "name1@mail.com", Gender.MALE, Status.ACTIVE, System.currentTimeMillis()),
        UserCacheEntity(2, "name2", "name2@mail.com", Gender.MALE, Status.ACTIVE,System.currentTimeMillis()),
        UserCacheEntity(3, "name3", "name3@mail.com", Gender.MALE, Status.ACTIVE,System.currentTimeMillis()),
        UserCacheEntity(5, "name4", "name4@mail.com", Gender.MALE, Status.ACTIVE,System.currentTimeMillis()),
    )

    private val mockUserList = arrayListOf(
        User(1, "name1", "name1@mail.com", Gender.MALE, Status.ACTIVE),
        User(2, "name2", "name2@mail.com", Gender.MALE, Status.ACTIVE),
        User(3, "name3", "name3@mail.com", Gender.MALE, Status.ACTIVE),
        User(5, "name4", "name4@mail.com", Gender.MALE, Status.ACTIVE)
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mainRepositoryImpl = spyk(MainRepositoryImpl(mockUserService, mockUserDao, mockUserNetworkMapper, mockUserCacheMapper))

    }

    @Test
    fun `add user with success`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        try {
            coEvery { mockUserService.addUser(mockUserNetworkMapper.mapFromDomainModel(mockUser)) } coAnswers { _ ->
                mockResponse
            }

            every { mockResponse.isSuccessful } answers { true }
            every { mockResponse.code() } answers { 201 }

            coEvery { mockUserDao.getUser(any()) } coAnswers { mockCacheEntity }
            every { mockUserCacheMapper.mapFromEntity(mockCacheEntity) } answers { mockUser }

            val user = mainRepositoryImpl.addUser(mockUser)
            Truth.assertThat(DataState.Success(mockUser)).isEqualTo(user)

        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `add user with error success response fail`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        try {
            coEvery { mockUserService.addUser(mockUserNetworkMapper.mapFromDomainModel(mockUser)) } coAnswers { _ ->
                mockResponse
            }
            every { mockResponse.isSuccessful } answers { false }
            coEvery { mockUserDao.getUser(any()) } coAnswers { mockCacheEntity }
            every { mockUserCacheMapper.mapFromEntity(mockCacheEntity) } answers { mockUser }

            val response = mainRepositoryImpl.addUser(mockUser)
            Truth.assertThat(DataState.Error("Something went wrong!")).isEqualTo(response)

        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `add user with error no internet`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        try {
            coEvery { mockUserService.addUser(mockUserNetworkMapper.mapFromDomainModel(mockUser)) } throws UnknownHostException()
            every { mockResponse.isSuccessful } answers { false }
            coEvery { mockUserDao.getUser(any()) } coAnswers { mockCacheEntity }
            every { mockUserCacheMapper.mapFromEntity(mockCacheEntity) } answers { mockUser }

            val response = mainRepositoryImpl.addUser(mockUser)
            Truth.assertThat(DataState.Error("No internet connection!")).isEqualTo(response)

        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `add user with error specific error code`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        try {
            coEvery { mockUserService.addUser(mockUserNetworkMapper.mapFromDomainModel(mockUser)) } coAnswers { _ ->
                mockResponse
            }
            every { mockResponse.isSuccessful } answers { false }
            every { mockResponse.code() } answers { 422 }

            coEvery { mockUserDao.getUser(any()) } coAnswers { mockCacheEntity }
            every { mockUserCacheMapper.mapFromEntity(mockCacheEntity) } answers { mockUser }

            val response = mainRepositoryImpl.addUser(mockUser)
            Truth.assertThat(DataState.Error("Data validation failed")).isEqualTo(response)

        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `get users with error fail get users`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        val mainRepositoryImpl = spyk(MainRepositoryImpl(mockUserService, mockUserDao, mockUserNetworkMapper, mockUserCacheMapper))

        try {
            coEvery { mainRepositoryImpl.getLastPage()  } coAnswers  { 10 }

            coEvery { mockUserService.getUsers(10) } coAnswers {
                mockGetUserResponseResponse
            }
            every { mockGetUserResponseResponse.isSuccessful } answers { false }

            every { mockGetUserResponseResponse.body() } answers { mockUserNetworkEntityList }

            every { mockUserNetworkMapper.mapFromEntitiesList(mockGetUserResponseResponse.body()!!) } answers  {
                mockUserList
            }

            coEvery { mockUserDao.getUsers() } coAnswers { mockUserCacheEntityList }

            every { mockUserCacheMapper.mapFromEntitiesList(mockUserCacheEntityList) } answers { mockUserList}

            Truth.assertThat(mainRepositoryImpl.getUsers()).isEqualTo(DataState.Error("Something went wrong!"))
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `get users with error fail get page`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        try {
            coEvery { mainRepositoryImpl.getLastPage()  } coAnswers { -1 }

            coEvery { mockUserService.getUsers(10) } coAnswers {
                mockGetUserResponseResponse
            }
            every { mockGetUserResponseResponse.isSuccessful } answers { true }

            every { mockGetUserResponseResponse.body() } answers { mockUserNetworkEntityList }

            every { mockUserNetworkMapper.mapFromEntitiesList(mockGetUserResponseResponse.body()!!) } answers  {
                mockUserList
            }

            coEvery { mockUserDao.getUsers() } coAnswers { mockUserCacheEntityList }

            every { mockUserCacheMapper.mapFromEntitiesList(mockUserCacheEntityList) } answers { mockUserList}

            Truth.assertThat(mainRepositoryImpl.getUsers()).isEqualTo(DataState.Error("Something went wrong!"))
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `get users with success`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        val mainRepositoryImpl = spyk(MainRepositoryImpl(mockUserService, mockUserDao, mockUserNetworkMapper, mockUserCacheMapper))

        try {
            coEvery { mainRepositoryImpl.getLastPage()  } coAnswers { 10 }

            coEvery { mockUserService.getUsers(10) } coAnswers {
                mockGetUserResponseResponse
            }
            every { mockGetUserResponseResponse.isSuccessful } answers { true }

            every { mockGetUserResponseResponse.body() } answers { mockUserNetworkEntityList }

            every { mockUserNetworkMapper.mapFromEntitiesList(mockGetUserResponseResponse.body()!!) } answers  {
                mockUserList
            }

            coEvery { mockUserDao.getUsers() } coAnswers { mockUserCacheEntityList }

            every { mockUserCacheMapper.mapFromEntitiesList(mockUserCacheEntityList) } answers { mockUserList}

            Truth.assertThat(mainRepositoryImpl.getUsers()).isEqualTo(DataState.Success(mockUserList))
        } finally {
            Dispatchers.resetMain()
        }
    }
}