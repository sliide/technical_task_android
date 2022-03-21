package com.sliide.domain.users.create

import com.sliide.interactor.users.create.NameErrors

interface ValidateNameCase {

    fun validate(name: String): NameErrors
}