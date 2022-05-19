package com.slide.test.repository.model

/**
 * Created by Stefan Halus on 19 May 2022
 */

enum class UserStatusModel(val key: String) {
    ACTIVE("active"),
    INACTIVE("inactive"),
    UNKNOWN("");

    companion object {
        fun fromKey(key: String): UserStatusModel {
            return values().firstOrNull { it.key == key } ?: UNKNOWN
        }
    }
}
