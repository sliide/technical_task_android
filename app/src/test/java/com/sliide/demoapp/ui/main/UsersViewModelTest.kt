package com.sliide.demoapp.ui.main

import com.sliide.demoapp.model.User
import com.sliide.demoapp.repository.MainRepository
import com.sliide.demoapp.utils.redux.Store
import com.sliide.demoapp.utils.redux.TestReducer
import com.sliide.demoapp.utils.response.DataState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UsersViewModelTest {

    lateinit var usersViewModel: UsersViewModel
    private var reducer = TestReducer()
    private val addUserViewState = UsersViewState()

    @RelaxedMockK
    lateinit var mockStore: Store<UsersViewState, UsersAction>

    @RelaxedMockK
    lateinit var mockRepo: MainRepository

    @RelaxedMockK
    lateinit var mockUser: User


    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        coEvery { mockStore.dispatch(UsersAction.FetchUsersStarted) } coAnswers { _ ->
            reducer.reduce(addUserViewState, UsersAction.FetchUsersStarted)
        }
        coEvery { mockStore.dispatch(UsersAction.FetchUsersCompleted(emptyList())) } coAnswers { _ ->
            reducer.reduce(addUserViewState, UsersAction.FetchUsersCompleted(emptyList()))
        }
        coEvery { mockStore.dispatch(UsersAction.FetchUsersFailed("Something went wrong!")) } coAnswers { _ ->
            reducer.reduce(addUserViewState, UsersAction.FetchUsersFailed("Something went wrong!"))
        }
        coEvery { mockStore.dispatch(UsersAction.AddUserStared) } coAnswers { _ ->
            reducer.reduce(addUserViewState, UsersAction.AddUserStared)
        }
        coEvery { mockStore.dispatch(UsersAction.AddUserCompleted(mockUser)) } coAnswers { _ ->
            reducer.reduce(addUserViewState, UsersAction.AddUserCompleted(mockUser))
        }
        coEvery { mockStore.dispatch(UsersAction.AddUserUserFailed("Something went wrong!")) } coAnswers { _ ->
            reducer.reduce(addUserViewState, UsersAction.AddUserUserFailed("Something went wrong!"))
        }
        coEvery { mockStore.dispatch(UsersAction.DeleteUserStarted) } coAnswers { _ ->
            reducer.reduce(addUserViewState, UsersAction.DeleteUserStarted)
        }
        coEvery { mockStore.dispatch(UsersAction.DeleteUserCompleted(10)) } coAnswers { _ ->
            reducer.reduce(addUserViewState, UsersAction.DeleteUserCompleted(10))
        }
        coEvery { mockStore.dispatch(UsersAction.DeleteUserFailed("Something went wrong!")) } coAnswers { _ ->
            reducer.reduce(addUserViewState, UsersAction.DeleteUserFailed("Something went wrong!"))
        }

    }

    @Test
    fun `get users with success`() = runTest {

        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        try {
            coEvery { mockRepo.getUsers() } coAnswers { DataState.Success(emptyList()) }

            usersViewModel = UsersViewModel(mockRepo, mockStore)
            usersViewModel.getUsers()

            reducer.assertActionProcessed(UsersAction.FetchUsersStarted)
            reducer.assertActionProcessed(UsersAction.FetchUsersCompleted(emptyList()))

        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `get users with error`() = runTest {

        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        try {
            coEvery { mockRepo.getUsers() } coAnswers { DataState.Error("Something went wrong!") }

            usersViewModel = UsersViewModel(mockRepo, mockStore)
            usersViewModel.getUsers()

            reducer.assertActionProcessed(UsersAction.FetchUsersStarted)
            reducer.assertActionProcessed(UsersAction.FetchUsersFailed("Something went wrong!"))

        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `add user with success`() = runTest {

        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        try {
            coEvery { mockRepo.addUser(mockUser) } coAnswers { DataState.Success(mockUser) }

            usersViewModel = UsersViewModel(mockRepo, mockStore)
            usersViewModel.addUser(mockUser)

            reducer.assertActionProcessed(UsersAction.AddUserStared)
            reducer.assertActionProcessed(UsersAction.AddUserCompleted(mockUser))
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `add user with error`() = runTest {

        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        try {
            coEvery { mockRepo.addUser(mockUser) } coAnswers { DataState.Error("Something went wrong!") }

            usersViewModel = UsersViewModel(mockRepo, mockStore)
            usersViewModel.addUser(mockUser)

            reducer.assertActionProcessed(UsersAction.AddUserStared)
            reducer.assertActionProcessed(UsersAction.AddUserUserFailed("Something went wrong!"))
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `delete user with success`() = runTest {

        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        try {
            coEvery { mockRepo.deleteUser(10) } coAnswers { DataState.Success(10) }

            usersViewModel = UsersViewModel(mockRepo, mockStore)
            usersViewModel.deleteUser(10)

            reducer.assertActionProcessed(UsersAction.DeleteUserStarted)
            reducer.assertActionProcessed(UsersAction.DeleteUserCompleted(10))
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `delete user with error`() = runTest {

        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        try {
            coEvery { mockRepo.deleteUser(10) } coAnswers { DataState.Error("Something went wrong!") }

            usersViewModel = UsersViewModel(mockRepo, mockStore)
            usersViewModel.deleteUser(10)

            reducer.assertActionProcessed(UsersAction.DeleteUserStarted)
            reducer.assertActionProcessed(UsersAction.DeleteUserFailed("Something went wrong!"))
        } finally {
            Dispatchers.resetMain()
        }
    }

}