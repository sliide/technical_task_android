package ru.santaev.techtask.feature.user.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.santaev.techtask.feature.user.domain.UserInteractor
import ru.santaev.techtask.utils.BaseViewModel
import ru.santaev.techtask.utils.Event
import ru.santaev.techtask.utils.showToast
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class UserCreationViewModel @Inject constructor(
    private val interactor: UserInteractor
) : BaseViewModel() {

    val name = MutableStateFlow("")
    val email = MutableStateFlow("")

    val isAddButtonEnabled = combine(name, email) { name, email ->
        name.isNotEmpty() && isValidEmail(email)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val exit = MutableStateFlow(Event(false))

    private val emailPattern = EMAIL_PATTERN.toPattern()

    fun onNameOrEmailChanged() {
        createErrorHandler()
    }

    fun onAddClicked() {
        viewModelScope.launch(createErrorHandler()) {
            runWithProgress {
                interactor.createUser(name.value, email.value)
                showToast("User has been added")
                exit.tryEmit(Event(true))
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return emailPattern.matcher(email).matches()
    }

    private fun createErrorHandler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, _ ->
            showToast("Something went wrong")
        }
    }

    companion object {
        private const val EMAIL_PATTERN = "^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*\$"
    }
}
