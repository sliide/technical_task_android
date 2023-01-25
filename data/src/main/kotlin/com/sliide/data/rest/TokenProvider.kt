package com.sliide.data.rest

interface TokenProvider {

    val token: String

    val tokenType: String
}