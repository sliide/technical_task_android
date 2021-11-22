package eu.andreihaiu.task.users

import androidx.lifecycle.Observer
import eu.andreihaiu.task.base.BaseTestClass
import eu.andreihaiu.task.data.base.ApiResponse
import eu.andreihaiu.task.data.models.AddUserResponse
import eu.andreihaiu.task.data.models.Users
import eu.andreihaiu.task.data.repos.UsersRepository
import eu.andreihaiu.task.viewmodels.users.UserEvent
import eu.andreihaiu.task.viewmodels.users.UsersViewModel
import eu.andreihaiu.task.viewmodels.users.UsersViewModelImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

class UsersViewModelTest : BaseTestClass() {

    @MockK
    lateinit var usersRepository: UsersRepository
    lateinit var usersViewModel: UsersViewModel

    @MockK
    lateinit var usersObserver: Observer<List<Users>>
    @MockK
    lateinit var eventObserver: Observer<UserEvent>

    // Given data
    private val user = Users(1, "Dorel", "dorel@mail.com", "male", "active")
    private val userList = listOf(user)
    private val userResponseSuccess = ApiResponse.Success(userList)
    private val userResponseError = ApiResponse.Error(400)
    private val newUser = Users(2, "dorela@gmail.com", "female", "active")
    private val newUserResponseSuccess = ApiResponse.Success(AddUserResponse(newUser))
    private val newUserResponseError = ApiResponse.Error(400)

    @Test
    @ExperimentalCoroutinesApi
    fun `Get users with success behaves as expected`() = runBlockingTest {
        // Mock data
        coEvery { usersRepository.getUsers() } returns userResponseSuccess

        // Init viewmodel
        usersViewModel = UsersViewModelImpl(usersRepository)

        // Add observers
        usersViewModel.users.observeForever(usersObserver)

        // Check function called
        verify(exactly = 1) { usersViewModel.getUsers() }

        // Check users updated
        verify(exactly = 1) { usersObserver.onChanged(userList) }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `Get users with error behaves as expected`() = runBlockingTest {
        // Mock data
        coEvery { usersRepository.getUsers() } returns userResponseError

        // Init viewmodel
        usersViewModel = UsersViewModelImpl(usersRepository)

        // Add observers
        usersViewModel.users.observeForever(usersObserver)

        // Check function called
        verify(exactly = 1) { usersViewModel.getUsers() }

        // Check users not updated
        verify(inverse = true) { usersObserver.onChanged(userList) }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `Add users with success behaves as expected`() = runBlockingTest {
        // Mock data
        coEvery { usersRepository.getUsers() } returns userResponseSuccess
        coEvery { usersRepository.addUser(any(), any(), any()) } returns newUserResponseSuccess

        // Init viewmodel
        usersViewModel = UsersViewModelImpl(usersRepository)

        // Call function
        usersViewModel.addUser("Dorela", "dorela@gmail.com", "female")

        // Check data is correct
        coVerify { usersRepository.addUser("Dorela", "dorela@gmail.com", "female") }
        verify { usersViewModel.getUsers() }
        assertEquals(usersViewModel.userEvent.value, UserEvent.ADD_SUCCES)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `Add users with error behaves as expected`() = runBlockingTest {
        // Mock data
        coEvery { usersRepository.getUsers() } returns userResponseSuccess
        coEvery { usersRepository.addUser(any(), any(), any()) } returns newUserResponseError

        // Init viewmodel
        usersViewModel = UsersViewModelImpl(usersRepository)

        // Add observer
        usersViewModel.userEvent.observeForever(eventObserver)

        // Call function
        usersViewModel.addUser("Dorela", "dorela@gmail.com", "female")

        // Check data is correct
        coVerify { usersRepository.addUser("Dorela", "dorela@gmail.com", "female") }
        verify(inverse = true) { eventObserver.onChanged(any()) }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `Delete users with success behaves as expected`() = runBlockingTest {
        // Mock data
        coEvery { usersRepository.getUsers() } returns userResponseSuccess
        coEvery { usersRepository.deleteUser(any()) } returns ApiResponse.Success(Response.success(204, Unit))

        // Init viewmodel
        usersViewModel = UsersViewModelImpl(usersRepository)

        // Add observer
        usersViewModel.userEvent.observeForever(eventObserver)

        // Call function
        usersViewModel.deleteUser(1)

        // Check data is correct
        coVerify { usersRepository.deleteUser(1) }
        verify(exactly = 1) { eventObserver.onChanged(any()) }
        assertEquals(usersViewModel.userEvent.value, UserEvent.DELETE_SUCCES)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `Delete users with error behaves as expected`() = runBlockingTest {
        // Mock data
        coEvery { usersRepository.getUsers() } returns userResponseSuccess
        coEvery { usersRepository.deleteUser(any()) } returns ApiResponse.Error(400)

        // Init viewmodel
        usersViewModel = UsersViewModelImpl(usersRepository)

        // Add observer
        usersViewModel.userEvent.observeForever(eventObserver)

        // Call function
        usersViewModel.deleteUser(1)

        // Check data is correct
        coVerify { usersRepository.deleteUser(1) }
        verify(inverse = true) { eventObserver.onChanged(any()) }
    }
}