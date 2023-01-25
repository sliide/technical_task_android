package com.sliide.presentation.users.create

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.Dialog
import com.google.accompanist.insets.imePadding
import com.google.accompanist.insets.statusBarsPadding
import com.sliide.presentation.R
import com.sliide.presentation.theme.Dimens
import com.sliide.presentation.theme.Shapes
import com.sliide.presentation.users.list.toErrorString

@Composable
fun CreateUserDialog(
    viewModel: CreateUserViewModel,
    create: (name: String, email: String) -> Unit,
    onDismiss: () -> Unit
) {
    val name by viewModel.name.collectAsState()
    val email by viewModel.email.collectAsState()

    val nameError by viewModel.nameError.collectAsState()
    val emailError by viewModel.emailError.collectAsState()

    val isCreate by viewModel.create.collectAsState()
    if (isCreate) {
        create(name, email)
        viewModel.createConsumed()
    }

    val resources = LocalContext.current.resources

    val onCancel = {
        viewModel.onCancelClick()
        onDismiss()
    }

    CreateUserDialog(
        name = name,
        email = email,
        nameError = nameError.toErrorString(resources),
        emailError = emailError.toErrorString(resources),
        onDismissRequest = onCancel,
        nameChanged = { text -> viewModel.onNameChanged(text) },
        emailChanged = { text -> viewModel.onEmailChanged(text) },
        onClickAdd = { viewModel.onAddClick() },
        onClickCancel = onCancel
    )
}

@Composable
private fun CreateUserDialog(
    name: String,
    email: String,
    nameError: String,
    emailError: String,
    onDismissRequest: () -> Unit,
    nameChanged: (String) -> Unit,
    emailChanged: (String) -> Unit,
    onClickAdd: () -> Unit,
    onClickCancel: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Box(
            modifier = Modifier
                .statusBarsPadding()
                .imePadding()
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card {
                Column(
                    modifier = Modifier.padding(
                        start = Dimens.large,
                        top = Dimens.large,
                        end = Dimens.large,
                        bottom = Dimens.default
                    )
                ) {
                    Text(text = stringResource(R.string.add_new_user))
                    Spacer(modifier = Modifier.height(Dimens.default))
                    OutlinedTextField(
                        value = name,
                        onValueChange = nameChanged,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = stringResource(R.string.name)) },
                        isError = nameError.isNotBlank(),
                        shape = Shapes.medium,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            imeAction = ImeAction.Next
                        )
                    )
                    Text(text = nameError, style = TextStyle(color = MaterialTheme.colors.error))
                    Spacer(modifier = Modifier.height(Dimens.small))
                    OutlinedTextField(
                        value = email,
                        onValueChange = emailChanged,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = stringResource(R.string.email)) },
                        isError = emailError.isNotBlank(),
                        shape = Shapes.medium,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = { onClickAdd() })
                    )
                    Text(text = emailError, style = TextStyle(color = MaterialTheme.colors.error))
                    Spacer(modifier = Modifier.height(Dimens.medium))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(onClick = onClickCancel) {
                            Text(text = stringResource(R.string.cancel))
                        }
                        Spacer(Modifier.width(Dimens.default))
                        Button(onClick = onClickAdd) {
                            Text(text = stringResource(R.string.add))
                        }
                    }
                }
            }
        }
    }
}