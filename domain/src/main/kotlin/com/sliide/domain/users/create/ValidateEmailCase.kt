package com.sliide.domain.users.create

import com.sliide.interactor.users.create.EmailErrors

interface ValidateEmailCase {

    fun validate(email: String): EmailErrors
}