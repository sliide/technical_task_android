package com.devforfun.sliidetask.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import com.devforfun.sliidetask.R
import com.devforfun.sliidetask.databinding.ActivityUsersBinding.inflate

interface DialogListener {
    fun onPositiveClick()
    fun onNegativeClick()
}

interface CreateUserDialogListener {
    fun onPositiveClick(name : String, email: String)
    fun onNegativeClick()
}

fun Context.noTitleYesOrNoDialog(message: String,
                                 positiveStringId: Int,
                                 negativeStringId: Int,
                                 dialogListener: DialogListener) : AlertDialog {
    return AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton(positiveStringId) { _, _ -> dialogListener.onPositiveClick() }
            .setNegativeButton(negativeStringId) { _, _ -> dialogListener.onNegativeClick() }
            .create()

}

fun Context.createUserDialog(message: String,
                             positiveStringId: Int,
                             negativeStringId: Int,
                             dialogListener: CreateUserDialogListener) : AlertDialog {

    val layoutInflater =  LayoutInflater.from(this)
    val view = layoutInflater.inflate(R.layout.create_user_layout, null)
    val nameEditText = view.findViewById(R.id.nameEditText) as EditText
    val emailEditText = view.findViewById(R.id.emailEditText) as EditText
    return AlertDialog.Builder(this)
        .setView(view)
        .setCancelable(false)
        .setMessage(message)
        .setPositiveButton(positiveStringId) { _, _ -> dialogListener.onPositiveClick(name = nameEditText.text.toString(),
                                                            email = emailEditText.text.toString()) }
        .setNegativeButton(negativeStringId) { _, _ -> dialogListener.onNegativeClick() }
        .create()

}