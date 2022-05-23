package com.slide.test.usecase.users

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.slide.test.core.Page
import com.slide.test.core.PageMetadata
import com.slide.test.core.Result
import com.slide.test.repository.UsersRepository
import com.slide.test.repository.model.GenderModel
import com.slide.test.repository.model.UserModel
import com.slide.test.repository.model.UserStatusModel
import com.slide.test.usecase.users.model.Gender
import com.slide.test.usecase.users.model.User
import com.slide.test.usecase.users.model.UserStatus
import com.slide.test.users.rules.RxTestSchedulerRule
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit

/**
 * Created by Stefan Halus on 23 May 2022
 */
class GetLatestUsersUseCaseImplementationTest {


    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testSchedulerRule = RxTestSchedulerRule()

    private lateinit var objectUnderTest: GetLatestUsersUseCaseImplementation

    private val usersRepository: UsersRepository = mock()
    private val creationTimeProvider: CreationTimeProvider = mock()


    @Before
    fun setUp() {
        objectUnderTest = GetLatestUsersUseCaseImplementation(usersRepository, creationTimeProvider)
        whenever(usersRepository.getUsers(null)).doReturn(Observable.just(getMockedPageResult()))
        //whenever(usersRepository.getUsers(100)).doReturn(Observable.just(getMockedPageResult()))

        whenever(creationTimeProvider.getCurrentTime()).doReturn(100)
        whenever(creationTimeProvider.getAppStartTime()).doReturn(90)
    }


    @Test
    fun `Load users success`() {
        // GIVEN
        val users = listOf(User(1, "name", "email", Gender.FEMALE, UserStatus.INACTIVE, 10),
            User(2, "name1", "email1", Gender.MALE, UserStatus.ACTIVE, 10))


        // WHEN
        testSchedulerRule.triggerActions()
        val testObserver = objectUnderTest.execute().test()
        testSchedulerRule.getScheduler().advanceTimeBy(1, TimeUnit.SECONDS)


        // THEN
        testObserver.assertValueCount(1)
        testObserver.assertValueAt(0) { result ->
            result is Result.Success && result.data == users
        }
    }

    @Test
    fun `Load users fail state`() {
        whenever(usersRepository.getUsers(null)).doReturn(Observable.just(Result.Error(Exception("Ex"))))
        // WHEN
        testSchedulerRule.triggerActions()
        val testObserver = objectUnderTest.execute().test()

        // THEN
        testObserver.assertValueCount(1)
        testObserver.assertValueAt(0) { result ->
            result is Result.Error && result.throwable is Exception
        }
    }


    private fun getMockedPageResult(): Result<Page<UserModel>> {
        return Result.Success(
            Page(
                200,
                PageMetadata(totalCount = 10000, pages = 100, currentPage = 1, pageSize = 20),
                data = listOf(
                    UserModel(1, "name", "email", GenderModel.FEMALE, UserStatusModel.INACTIVE),
                    UserModel(2, "name1", "email1", GenderModel.MALE, UserStatusModel.ACTIVE)
                )
            )
        )
    }

}