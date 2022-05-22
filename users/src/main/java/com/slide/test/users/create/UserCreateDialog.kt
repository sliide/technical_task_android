package com.slide.test.users.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.slide.test.core_ui.component.CustomChip
import com.slide.test.core_ui.component.InfoDialog
import com.slide.test.core_ui.component.LoadingOverlay
import com.slide.test.usecase.users.model.Gender
import com.slide.test.users.R

/**
 * Created by Stefan Halus on 20 May 2022
 */
@Composable
fun UserCreateDialogRoute(
    modifier: Modifier = Modifier,
    onDismiss: (Boolean) -> Unit,
    viewModel: UserCreateViewModel = hiltViewModel()
) {
    val viewState by viewModel.observableState.subscribeAsState(initial = viewModel.initialState)

    UserCreateDialog(
        viewState = viewState,
        errorAcknowledged = { viewModel.dispatch(Action.ErrorAcknowledge) },
        onDismiss = onDismiss,
        onSubmit = { name, email, gender -> viewModel.dispatch(Action.UserCreateRequest(name, email, gender)) },
        userNameChanged = { viewModel.dispatch(Action.UserFormUpdated(userName = it)) },
        emailChanged = { viewModel.dispatch(Action.UserFormUpdated(email = it)) },
        genderSelectionChanged = { viewModel.dispatch(Action.UserFormUpdated(selectedGenderIndex = it)) }

    )

}

@Composable
fun UserCreateDialog(
    modifier: Modifier = Modifier,
    viewState: State,
    onDismiss: (Boolean) -> Unit,
    onSubmit: (String, String, Int) -> Unit = { _, _, _ -> },
    errorAcknowledged: () -> Unit,
    userNameChanged: (String) -> Unit,
    emailChanged: (String) -> Unit,
    genderSelectionChanged: (Int) -> Unit
) {
    when {
        viewState.isLoading -> LoadingOverlay()
        viewState.userCreateSuccess == false -> ErrorDialog(viewState.errorMessage, errorAcknowledged)
        viewState.userCreateSuccess == true -> SuccessDialog(viewState.createdUserName ?: "") { onDismiss(true) }
        else -> CreateUserForm(
            genderOptions = viewState.genderOptions,
            userName = viewState.userNameInput ?: "",
            email = viewState.emailInput ?: "",
            genderSelectedIndex = viewState.genderInput,
            isEmailError = viewState.isEmailError,
            onCancel = { onDismiss(false) },
            onSubmit = onSubmit,
            userNameChanged = userNameChanged,
            emailChanged = emailChanged,
            genderSelectionChanged = genderSelectionChanged
        )

    }
}

@Composable
fun CreateUserForm(
    genderOptions: List<Gender>,
    userName: String = "",
    email: String = "",
    genderSelectedIndex: Int = 0,
    isEmailError: Boolean = false,
    onCancel: () -> Unit = {},
    onSubmit: (String, String, Int) -> Unit = { _, _, _ -> },
    userNameChanged: (String) -> Unit,
    emailChanged: (String) -> Unit,
    genderSelectionChanged: (Int) -> Unit
) {

    Column(
        Modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.extraLarge
            )
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.user_create_dialog_title), style = MaterialTheme.typography.titleLarge)

        TextField(
            value = userName,
            onValueChange = { userNameChanged(it) },
            label = { Text(text = stringResource(R.string.user_create_name_hint)) },
            singleLine = true
        )

        TextField(
            value = email,
            onValueChange = { emailChanged(it) },
            label = { Text(text = stringResource(R.string.user_create_email_hint)) },
            isError = isEmailError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Text(text = stringResource(R.string.user_create_gender_hint), style = MaterialTheme.typography.bodyLarge)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            genderOptions.forEachIndexed { index, item ->
                CustomChip(
                    checked = genderSelectedIndex == index,
                    onCheckedChanged = { checked -> if (checked) genderSelectionChanged(index) },
                    text = item.name
                )
            }
        }

        Row(
            Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                colors = ButtonDefaults.outlinedButtonColors(),
                onClick = onCancel
            ) {
                Text(stringResource(R.string.user_create_cancel_button))
            }

            Button(modifier = Modifier.padding(start = 8.dp),
                onClick = {
                    onSubmit(userName, email, genderSelectedIndex)
                }) {
                Text(stringResource(R.string.user_create_save_button))
            }
        }
    }
}

@Composable
fun SuccessDialog(userName: String = "", onDismiss: () -> Unit) {
    InfoDialog(
        title = stringResource(R.string.dialog_info_success_title),
        text = stringResource(R.string.user_create_dialog_success_message, userName),
        buttonText = stringResource(R.string.dialog_button_ok),
        onApprove = onDismiss,
        onDismiss = onDismiss
    )

}

@Composable
fun ErrorDialog(
    errorMessage: String?,
    onDismiss: () -> Unit
) {
    InfoDialog(
        title = "Error",
        text = errorMessage ?: "",
        buttonText = "Ok",
        onApprove = onDismiss,
        onDismiss = onDismiss
    )

}
