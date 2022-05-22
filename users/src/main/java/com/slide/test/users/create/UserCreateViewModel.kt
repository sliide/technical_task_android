package com.slide.test.users.create

import android.util.Log
import com.slide.test.core.errors.EmailAlreadyExistsException
import com.slide.test.core.errors.GoRestApiException
import com.slide.test.core_ui.mvi.BaseViewModel
import com.slide.test.core_ui.mvi.Reducer
import com.slide.test.usecase.users.CreateUserUseCase
import com.slide.test.usecase.users.model.CreateUserRequest
import com.slide.test.usecase.users.model.Gender
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

/**
 * Created by Stefan Halus on 18 May 2022
 */
@HiltViewModel
class UserCreateViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase
) : BaseViewModel<Action, State>() {

    private val genderOptions = listOf(Gender.FEMALE, Gender.MALE)

    override val initialState: State = State(isIdle = true, genderOptions = genderOptions)

    private val reducer: Reducer<State, Change> = { state, change ->
        when (change) {
            is Change.Loading -> state.copy(
                isIdle = false,
                isLoading = true,
                errorMessage = null
            )
            is Change.UserCreateError -> state.copy(
                isIdle = false,
                isLoading = false,
                userCreateSuccess = false,
                errorMessage = extractErrorMessages(change.error),
                isEmailError = change.error is EmailAlreadyExistsException
            )
            is Change.UserCreateSuccess -> state.copy(
                isIdle = false,
                isLoading = false,
                userCreateSuccess = true,
                errorMessage = null,
                createdUserName = change.createdUserName
            )
            is Change.UserCreationAcknowledged -> state.copy(
                userCreateSuccess = null,
                errorMessage = null
            )
            is Change.UserFormUpdate -> state.copy(
                isIdle = false,
                userCreateSuccess = null,
                errorMessage = null,
                userNameInput = change.userName ?: state.userNameInput,
                emailInput = change.email ?: state.emailInput,
                genderInput = change.genderIndex ?: state.genderInput
            )
        }
    }

    init {
        bindActions()
    }

    private fun bindActions() {
        val allChanges = listOf(
            userCreateAction(),
            userFormUpdates(),
            userErrorAcknowledged()
        )

        disposables.add(Observable.merge(allChanges)
            .scan(initialState, reducer)
            .filter { !it.isIdle }
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(state::accept) { Log.e("TAG", it.stackTraceToString()) })
    }

    private fun userCreateAction(): Observable<Change> {
        return actions.ofType(Action.UserCreateRequest::class.java)
            .switchMap { action ->
                createUserUseCase.execute(CreateUserRequest(action.userName, action.email, genderOptions[action.selectedGenderIndex]))
                    .toSingleDefault<Change>(Change.UserCreateSuccess(action.userName))
                    .toObservable()
                    .startWithItem(Change.Loading)
                    .onErrorReturn { Change.UserCreateError(action.userName, action.email, action.selectedGenderIndex, it) }
            }
    }

    private fun userErrorAcknowledged(): Observable<Change> {
        return actions.ofType(Action.ErrorAcknowledge::class.java)
            .map<Change> { Change.UserCreationAcknowledged }
    }

    private fun userFormUpdates(): Observable<Change> {
        return actions.ofType(Action.UserFormUpdated::class.java)
            .map<Change> { Change.UserFormUpdate(it.userName, it.email, it.selectedGenderIndex) }
    }

    private fun extractErrorMessages(error: Throwable): String {
        return when(error) {
            is GoRestApiException -> error.errors.joinToString("\n") { "${it.field} ${it.message}"}
            else -> error.localizedMessage
        }
    }
}
