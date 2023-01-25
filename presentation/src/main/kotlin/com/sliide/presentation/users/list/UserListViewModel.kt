package com.sliide.presentation.users.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.sliide.interactor.users.list.AddResult
import com.sliide.interactor.users.list.DeleteResult
import com.sliide.interactor.users.list.UserItem
import com.sliide.interactor.users.list.UserListInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserListViewModel @Inject constructor(
    private val interactor: UserListInteractor,
    private val pagingSourceFactory: PagingSourceFactory
) : ViewModel() {

    private val mutableDialog = MutableStateFlow<Dialogs>(Dialogs.None)
    internal val dialog: StateFlow<Dialogs> = mutableDialog

    private val mutableMessage = MutableStateFlow(Message.NONE)
    internal val message: StateFlow<Message> = mutableMessage

    private val modificationEvents = MutableStateFlow<List<UserItemEvents>>(emptyList())

    internal val items = Pager(PagingConfig(pageSize = 20, maxSize = 200)) {
        pagingSourceFactory.create()
    }
        .flow
        .cachedIn(viewModelScope)
        .combine(modificationEvents) { pagingData, modifications ->
            modifications.fold(pagingData) { paging, event ->
                when (event) {
                    is UserItemEvents.Add -> paging.insertHeaderItem(item = event.item)
                    is UserItemEvents.Remove -> paging.filter { item -> event.userId != item.id }
                }
            }
        }

    fun addUser(name: String, email: String) {
        viewModelScope.launch {
            mutableDialog.value = Dialogs.None

            when (val result = interactor.addNewUser(name, email)) {
                AddResult.FieldsError -> mutableMessage.value = Message.CHECK_FIELDS
                is AddResult.UnknownError -> mutableMessage.value = Message.UNKNOWN
                is AddResult.EmailAlreadyTaken -> mutableMessage.value = Message.EMAIL_ALREADY_TAKEN
                is AddResult.Created -> {
                    modificationEvents.value += UserItemEvents.Add(result.user)
                    mutableMessage.value = Message.USER_ADDED
                }
            }
        }
    }

    fun removeUser(userId: Int) {
        mutableDialog.value = Dialogs.None

        viewModelScope.launch {
            when (interactor.deleteUser(userId)) {
                DeleteResult.Deleted -> modificationEvents.value += UserItemEvents.Remove(userId)
                is DeleteResult.UnknownError -> mutableMessage.value = Message.UNKNOWN
            }
        }
    }

    fun onDismissDialog() {
        mutableDialog.value = Dialogs.None
    }

    internal fun onItemLongClick(item: UserItem) {
        mutableDialog.value = Dialogs.ConfirmRemove(item.id)
    }

    internal fun onFabClick() {
        mutableDialog.value = Dialogs.CreateUser
    }

    internal fun loadListError() {
        mutableMessage.value = Message.LOADING_LIST_FAILURE
    }

    internal fun onSnackAction() {
        mutableMessage.value = Message.NONE
    }
}