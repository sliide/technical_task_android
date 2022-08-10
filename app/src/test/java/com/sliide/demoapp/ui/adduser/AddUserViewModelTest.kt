package com.sliide.demoapp.ui.adduser

import com.sliide.demoapp.model.Gender
import com.sliide.demoapp.model.Status
import com.sliide.demoapp.utils.redux.Store
import com.sliide.demoapp.utils.redux.TestReducer
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
class AddUserViewModelTest {

    lateinit var addUserViewModel: AddUserViewModel

    private var reducer = TestReducer()

    @RelaxedMockK
    lateinit var store: Store<AddUserViewState, AddUserAction>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `name changed`() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        try {
            val addUserViewState = AddUserViewState()
            val action = AddUserAction.NameChanged("DEMO NAME")
            coEvery { store.dispatch(any()) } coAnswers { _ ->
                reducer.reduce(addUserViewState, action)
            }

            addUserViewModel = AddUserViewModel(store)
            addUserViewModel.nameChanged("DEMO NAME")

            reducer.assertActionProcessed(AddUserAction.NameChanged("DEMO NAME"))

        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `email changed`() = runTest {

        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        try {
            val addUserViewState = AddUserViewState()
            val action = AddUserAction.EmailChanged("test@test.com")
            coEvery { store.dispatch(any()) } coAnswers { _ ->
                reducer.reduce(addUserViewState, action)
            }

            addUserViewModel = AddUserViewModel(store)
            addUserViewModel.emailChanged("test@test.com")

            reducer.assertActionProcessed(AddUserAction.EmailChanged("test@test.com"))
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `gender changed`() = runTest {

        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        try {
            val addUserViewState = AddUserViewState()
            val action = AddUserAction.GenderChanged(Gender.MALE)
            coEvery { store.dispatch(any()) } coAnswers { _ ->
                reducer.reduce(addUserViewState, action)
            }

            addUserViewModel = AddUserViewModel(store)
            addUserViewModel.genderChanged(Gender.MALE)

            reducer.assertActionProcessed(AddUserAction.GenderChanged(Gender.MALE))
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `status changed`() = runTest {

        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        try {
            val addUserViewState = AddUserViewState()
            val action = AddUserAction.StatusChanged(Status.ACTIVE)
            coEvery { store.dispatch(any()) } coAnswers { _ ->
                reducer.reduce(addUserViewState, action)
            }

            addUserViewModel = AddUserViewModel(store)
            addUserViewModel.statusChanged(Status.ACTIVE)

            reducer.assertActionProcessed(AddUserAction.StatusChanged(Status.ACTIVE))
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `saved button clicked`() = runTest {

        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        try {
            val addUserViewState = AddUserViewState()
            val action = AddUserAction.SaveButtonClicked
            coEvery { store.dispatch(any()) } coAnswers { _ ->
                reducer.reduce(addUserViewState, action)
            }

            addUserViewModel = AddUserViewModel(store)
            addUserViewModel.savedButtonClicked()

            reducer.assertActionProcessed(AddUserAction.SaveButtonClicked)
        } finally {
            Dispatchers.resetMain()
        }
    }
}