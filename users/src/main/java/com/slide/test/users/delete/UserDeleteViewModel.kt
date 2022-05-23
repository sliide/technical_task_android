package com.slide.test.users.delete

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.slide.test.core_ui.mvi.BaseViewModel
import com.slide.test.core_ui.mvi.Reducer
import com.slide.test.usecase.users.DeleteUserUseCase
import com.slide.test.users.navigation.UserDeleteDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

/**
 * Created by Stefan Halus on 18 May 2022
 */
@HiltViewModel
class UserDeleteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val deleteUserUseCase: DeleteUserUseCase
) : BaseViewModel<Action, State>() {

    private val userId: Long = checkNotNull(
        savedStateHandle[UserDeleteDestination.Input.userIdArg]
    )

    private val userName: String = checkNotNull(
        savedStateHandle[UserDeleteDestination.Input.userNameArg]
    )

    override val initialState: State = State(isIdle = true, userName = userName)

    private val reducer: Reducer<State, Change> = { state, change ->
        when (change) {
            is Change.Loading -> state.copy(
                isIdle = false,
                isLoading = true,
                errorMessage = null
            )
            is Change.DeleteUserError -> state.copy(
                isIdle = false,
                isLoading = false,
                errorMessage = change.error?.message,
                userDeleteSuccess = false
            )
            is Change.DeleteUserSuccess -> state.copy(
                isIdle = false,
                isLoading = false,
                errorMessage = null,
                userDeleteSuccess = true
            )
            is Change.UserDeleteAcknowledged -> initialState.copy(isIdle = false)
        }
    }

    init {
        bindActions()
    }

    private fun bindActions() {
        val allChanges = listOf(
            userDeleteApprove(),
            userDeleteAck()
        )

        disposables.add(Observable.merge(allChanges)
            .scan(initialState, reducer)
            .filter { !it.isIdle }
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(state::accept) { Log.e("TAG", it.stackTraceToString()) })
    }

    private fun userDeleteApprove(): Observable<Change> {
        return actions.ofType(Action.UserDeleteConfirmation::class.java)
            .switchMap {
                deleteUserUseCase.execute(userId)
                    .toSingleDefault<Change>(Change.DeleteUserSuccess)
                    .toObservable()
                    .startWithItem(Change.Loading)
                    .onErrorReturn { Change.DeleteUserError(it) }
            }
    }

    private fun userDeleteAck(): Observable<Change> {
        return actions.ofType(Action.UserDeleteResultAcknowledge::class.java)
            .map<Change> { Change.UserDeleteAcknowledged }
    }

}
