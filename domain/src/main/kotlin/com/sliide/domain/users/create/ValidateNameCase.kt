package com.sliide.domain.users

import com.sliide.interactor.users.NameErrors

interface ValidateNameCase {

    fun validate(name: String): NameErrors
}