package com.sliide.presentation.users.list

import android.content.res.Resources
import com.sliide.interactor.users.create.EmailErrors
import com.sliide.interactor.users.create.NameErrors
import com.sliide.presentation.R

fun NameErrors.toErrorString(resources: Resources): String = when (this) {
    NameErrors.NAME_REQUIRED -> resources.getString(R.string.field_required)
    NameErrors.NONE -> ""
}

fun EmailErrors.toErrorString(resources: Resources): String = when (this) {
    EmailErrors.EMAIL_REQUIRED -> resources.getString(R.string.field_required)
    EmailErrors.INCORRECT_FORMAT -> resources.getString(R.string.enter_valid_email)
    EmailErrors.NONE -> ""
}

fun Errors.toErrorString(resources: Resources): String = when (this) {
    Errors.ADD_USER_FIELD -> resources.getString(R.string.users_field_error)
    Errors.UNKNOWN -> resources.getString(R.string.unknown_error)
    Errors.NONE -> ""
}