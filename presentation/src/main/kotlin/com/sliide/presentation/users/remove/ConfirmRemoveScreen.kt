package com.sliide.presentation.users.remove

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sliide.presentation.R
import com.sliide.presentation.theme.Dimens

@Composable
fun ConfirmRemoveScreen(
    confirm: () -> Unit,
    onDismiss: () -> Unit
) {
    ConfirmRemoveDialog(
        onDismissRequest = onDismiss,
        onClickConfirm = { confirm() },
        onClickCancel = onDismiss
    )
}

@Composable
private fun ConfirmRemoveDialog(
    onDismissRequest: () -> Unit,
    onClickConfirm: () -> Unit,
    onClickCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        text = { Text(text = stringResource(R.string.remove_confirmation)) },
        buttons = {
            Row(
                modifier = Modifier
                    .padding(
                        start = Dimens.large,
                        end = Dimens.large,
                        bottom = Dimens.default
                    )
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = onClickCancel) {
                    Text(text = stringResource(R.string.cancel))
                }
                Spacer(Modifier.width(Dimens.default))
                Button(onClick = onClickConfirm) {
                    Text(text = stringResource(R.string.confirm))
                }
            }
        }
    )
}