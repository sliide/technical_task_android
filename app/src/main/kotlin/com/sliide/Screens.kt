package com.sliide

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sliide.di.appProvider
import com.sliide.di.users.create.DaggerCreateUserComponent
import com.sliide.di.users.list.DaggerUserListComponent
import com.sliide.presentation.components.FullScreenProgress
import com.sliide.presentation.users.create.CreateUserDialog
import com.sliide.presentation.users.create.CreateUserViewModel
import com.sliide.presentation.users.list.Dialogs
import com.sliide.presentation.users.list.UserListScreen
import com.sliide.presentation.users.list.UserListViewModel
import com.sliide.presentation.users.remove.ConfirmRemoveScreen
import java.util.*

@Composable
internal fun UserListScreen() {
    val appProvider = LocalContext.current.appProvider()

    val component = DaggerUserListComponent.builder()
        .appProvider(appProvider)
        .build()

    val viewModel = viewModel(
        modelClass = UserListViewModel::class.java,
        factory = component.viewModelFactory()
    )

    UserListScreen(viewModel) { dialog ->
        when (dialog) {
            Dialogs.CreateUser -> CreateUserScreen(
                create = { name: String, email: String -> viewModel.addUser(name, email) },
                onDismiss = { viewModel.onDismissDialog() })

            is Dialogs.ConfirmRemove -> ConfirmRemoveScreen(
                confirm = { viewModel.removeUser(dialog.userId) },
                onDismiss = { viewModel.onDismissDialog() }
            )

            Dialogs.Progress -> FullScreenProgress()

            Dialogs.None -> { // ignore it. when is using for exhausted feature
            }
        }
    }
}

@Composable
private fun CreateUserScreen(
    create: (name: String, email: String) -> Unit,
    onDismiss: () -> Unit
) {
    val appProvider = LocalContext.current.appProvider()

    val component = DaggerCreateUserComponent.builder()
        .appProvider(appProvider)
        .build()

    val viewModel = viewModel(
        modelClass = CreateUserViewModel::class.java,
        factory = component.viewModelFactory()
    )

    CreateUserDialog(viewModel, create, onDismiss)
}