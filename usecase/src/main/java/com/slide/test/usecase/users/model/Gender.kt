package com.slide.test.usecase.users.model

import com.slide.test.repository.model.GenderModel

/**
 * Created by Stefan Halus on 19 May 2022
 */
enum class Gender {
    FEMALE,
    MALE,
    UNKNOWN
}

fun GenderModel.toUseCase() : Gender {
   return when(this) {
        GenderModel.FEMALE -> Gender.FEMALE
        GenderModel.MALE -> Gender.MALE
        GenderModel.UNKNOWN -> Gender.UNKNOWN
    }
}

fun Gender.toModel() : GenderModel {
    return when(this) {
        Gender.FEMALE -> GenderModel.FEMALE
        Gender.MALE -> GenderModel.MALE
        Gender.UNKNOWN -> GenderModel.UNKNOWN
    }
}