package com.sliide.demoapp.model

/**
 * Enum to define Status
 *
 * @param statusValue the status of a [User]
 */
enum class Status(val statusValue: String) {
    ACTIVE("active"), INACTIVE("inactive")
}