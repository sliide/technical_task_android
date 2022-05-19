package com.slide.test.users

import android.util.Log
import com.slide.test.core.Result
import com.slide.test.core.TimeFormatter
import com.slide.test.core.map
import com.slide.test.core_ui.mvi.BaseViewModel
import com.slide.test.core_ui.mvi.Reducer
import com.slide.test.usecase.users.GetLatestUsersUseCase
import com.slide.test.users.listing.Action
import com.slide.test.users.listing.Change
import com.slide.test.users.listing.State
import com.slide.test.users.model.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Stefan Halus on 18 May 2022
 */
@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getLatestUsersUseCase: GetLatestUsersUseCase,
    private val timeFormatter: TimeFormatter
) : BaseViewModel<Action, State>() {

    override val initialState: State = State(isIdle = true)

    private val reducer: Reducer<State, Change> = { state, change ->
        when (change) {
            is Change.Loading -> state.copy(
                isIdle = false,
                isLoading = true,
                userList = emptyList(),
                isError = false
            )
            is Change.UserList -> state.copy(
                isLoading = false,
                userList = change.userList
            )
            is Change.Error -> state.copy(
                isLoading = false,
                isError = true
            )
            is Change.EmptyUserList -> state.copy(
                isLoading = false,
                isEmpty = true
            )
            is Change.AddUserError -> TODO()
            is Change.AddUserSuccess -> TODO()
            is Change.DeleteUserError -> TODO()
            is Change.DeleteUserSuccess -> TODO()
            is Change.ShowDeletePopup -> TODO()
            is Change.ShowDeleteUserConfirmation -> TODO()
            Change.ShowAddUserPopup -> TODO()
        }
    }

    init {
        bindActions()
        dispatch(Action.LoadUserList)
    }

    private fun bindActions() {
        val allChanges = Observable.merge(userListChanges(), showAddUserPopupChanges())

        disposables.add(allChanges
            .scan(initialState, reducer)
            .filter { !it.isIdle }
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(state::accept) { Log.e("TAG", it.stackTraceToString()) })
    }

    private fun showAddUserPopupChanges(): ObservableSource<out Change>? {
        return actions.ofType(Action.AddUserButtonTapped::class.java)
            .map { Change.ShowAddUserPopup }
    }

    private fun userListChanges(): Observable<Change> {
        return actions.ofType(Action.LoadUserList::class.java)
            .switchMap {
                getLatestUsersUseCase.execute()
                    .map { result ->
                        result.map { userList ->
                            userList.map { user -> user.toUI(timeFormatter) }
                        }
                    }
                    .subscribeOn(Schedulers.io())
                    .map<Change> {
                        when (it) {
                            is Result.Success -> Change.UserList(it.data)
                            is Result.Error -> Change.Error(it.throwable!!)
                            is Result.Loading -> Change.Loading
                        }
                    }
                    .defaultIfEmpty(Change.EmptyUserList)
                    .onErrorReturn { Change.Error(it) }
                    .startWithItem(Change.Loading)
            }
    }
}
