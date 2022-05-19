package com.slide.test.repository.model

/**
 * Created by Stefan Halus on 19 May 2022
 */
enum class GenderModel(val key: String) {
    FEMALE("female"),
    MALE("male"),
    UNKNOWN("");

    companion object {
        fun fromKey(key: String): GenderModel {
            return values().firstOrNull { it.key == key } ?: UNKNOWN
        }
    }
}
