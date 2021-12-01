package ru.santaev.techtask.feature.user.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.github.ajalt.timberkt.Timber
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import ru.santaev.techtask.feature.user.domain.UserInteractor
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class UsersViewModel @Inject constructor(
    private val interactor: UserInteractor
) : ViewModel() {

    private val updateUserListRequest = MutableStateFlow(UpdateUserListRequest())
    val users = updateUserListRequest.map { interactor.getUsers() }
        .catch { Timber.e(it) { "Error while loading users" } }
        .asLiveData()

    private class UpdateUserListRequest
}