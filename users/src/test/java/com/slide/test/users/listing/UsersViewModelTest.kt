package com.slide.test.users.listing

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import com.slide.test.core.Result
import com.slide.test.core.TimeFormatter
import com.slide.test.usecase.users.GetLatestUsersUseCase
import com.slide.test.usecase.users.model.Gender
import com.slide.test.usecase.users.model.User
import com.slide.test.usecase.users.model.UserStatus
import com.slide.test.users.model.UserUI
import com.slide.test.users.rules.RxTestSchedulerRule
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Stefan Halus on 23 May 2022
 */
class UsersViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testSchedulerRule = RxTestSchedulerRule()

    private lateinit var objectUnderTest: UsersViewModel

    private val loadingState = State(isLoading = true)

    private val getLatestUsersUseCase:GetLatestUsersUseCase = mock()

    private val timeFormatter: TimeFormatter = mock()


    @Before
    fun setUp() {
        whenever(timeFormatter.formatDuration(any())).doReturn("formatted_time")
    }


    @Test
    fun `Load users success state`() {
        // GIVEN
        val usersList = listOf(UserUI(1, "name", "email", Gender.MALE, UserStatus.ACTIVE, "formatted_time"))
        val successState = State(isIdle = false, userList = usersList)

        val users = listOf(User(1, "name", "email", Gender.MALE, UserStatus.ACTIVE, 1000))
        whenever(getLatestUsersUseCase.execute()).doReturn(Observable.just(Result.Success(users)))

        // WHEN
        objectUnderTest = UsersViewModel(getLatestUsersUseCase, timeFormatter)
        val testObserver = objectUnderTest.observableState.test()
        testSchedulerRule.triggerActions()

        // THEN
        testObserver.assertValueCount(2)
        testObserver.assertValueAt(0, loadingState)
        testObserver.assertValueAt(1, successState)
    }

    @Test
    fun `Load users fail state`() {
        // GIVEN
        val errorState = State(isIdle = false, errorMessage = "Error")
        whenever(getLatestUsersUseCase.execute()).doReturn(Observable.just(Result.Error(Exception("Error"))))

        // WHEN
        objectUnderTest = UsersViewModel(getLatestUsersUseCase, timeFormatter)
        val testObserver = objectUnderTest.observableState.test()

        testSchedulerRule.triggerActions()

        // THEN
        testObserver.assertValueCount(2)
        testObserver.assertValueAt(0, loadingState)
        testObserver.assertValueAt(1, errorState)
    }

}