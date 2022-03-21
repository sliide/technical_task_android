package com.sliide.presentation.users.list

sealed class Dialogs {
    object CreateUser : Dialogs()
    data class ConfirmRemove(val userId: Int) : Dialogs()
    object Progress : Dialogs()
    object None : Dialogs()
}