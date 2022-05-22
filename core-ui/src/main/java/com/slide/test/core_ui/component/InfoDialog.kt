package com.slide.test.core_ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

/**
 * Created by Stefan Halus on 20 May 2022
 */
@Composable
fun InfoDialog(
    title: String = "",
    text: String = "",
    buttonText: String = "",
    onDismiss: () -> Unit,
    onApprove: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = title) },
        text = { Text(text = text) },
        confirmButton = {
            Button(onClick = { onApprove()  }) {
                Text(buttonText)
            }
        }
    )
}