package com.slide.test.users.delete

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.slide.test.usecase.users.DeleteUserUseCase
import com.slide.test.users.navigation.UserDeleteDestination
import com.slide.test.users.rules.RxTestSchedulerRule
import io.reactivex.rxjava3.core.Completable
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Stefan Halus on 23 May 2022
 */
class UserDeleteViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testSchedulerRule = RxTestSchedulerRule()

    private lateinit var objectUnderTest: UserDeleteViewModel

    private val loadingState = State(isLoading = true, userName = "test name")

    private val deleteUsersUseCase: DeleteUserUseCase = mock()

    private val savedStateHandle: SavedStateHandle = mock()


    @Before
    fun setUp() {
        whenever(savedStateHandle.get<Long>(UserDeleteDestination.Input.userIdArg)).doReturn(100)
        whenever(savedStateHandle.get<String>(UserDeleteDestination.Input.userNameArg)).doReturn("test name")
    }


    @Test
    fun `Delete user success state`() {
        // GIVEN
        val successState = State(isIdle = false, userName = "test name", userDeleteSuccess = true)

        whenever(deleteUsersUseCase.execute(100)).doReturn(Completable.complete())

        // WHEN
        objectUnderTest = UserDeleteViewModel(savedStateHandle, deleteUsersUseCase)
        val testObserver = objectUnderTest.observableState.test()
        objectUnderTest.dispatch(Action.UserDeleteConfirmation)

        testSchedulerRule.triggerActions()

        // THEN
        testObserver.assertValueCount(2)
        testObserver.assertValueAt(0, loadingState)
        testObserver.assertValueAt(1, successState)
    }

    @Test
    fun `Delete user fail state`() {
        // GIVEN
        val errorState = State(isIdle = false, userName = "test name", userDeleteSuccess = false, errorMessage = "Error")
        whenever(deleteUsersUseCase.execute(100)).doReturn(Completable.error(Exception("Error")))

        // WHEN
        objectUnderTest = UserDeleteViewModel(savedStateHandle, deleteUsersUseCase)
        val testObserver = objectUnderTest.observableState.test()
        objectUnderTest.dispatch(Action.UserDeleteConfirmation)

        testSchedulerRule.triggerActions()

        // THEN
        testObserver.assertValueCount(2)
        testObserver.assertValueAt(0, loadingState)
        testObserver.assertValueAt(1, errorState)
    }

}