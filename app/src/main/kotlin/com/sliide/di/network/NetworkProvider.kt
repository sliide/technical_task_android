package com.sliide.di.network

import com.sliide.data.rest.ServicesProvider

interface NetworkProvider {

    fun servicesProvider(): ServicesProvider
}