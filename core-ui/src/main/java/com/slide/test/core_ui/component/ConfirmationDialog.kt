package com.slide.test.core_ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

/**
 * Created by Stefan Halus on 20 May 2022
 */
@Composable
fun ConfirmationDialog(
    title: String = "",
    text: String = "",
    confirmActionText: String = "",
    dismissActionText: String = "",
    onCancel: () -> Unit,
    onApprove: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onCancel() },
        title = { Text(text = title) },
        text = { Text(text = text) },
        confirmButton = {
            Button(onClick = { onApprove() }) {
                Text(confirmActionText)
            }
        },
        dismissButton = {
            Button(onClick = { onCancel() }) {
                Text(dismissActionText)
            }
        }
    )
}