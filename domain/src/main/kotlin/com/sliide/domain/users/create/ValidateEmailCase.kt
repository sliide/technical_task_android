package com.sliide.domain.users

import com.sliide.interactor.users.EmailErrors

interface ValidateEmailCase {

    fun validate(email: String): EmailErrors
}