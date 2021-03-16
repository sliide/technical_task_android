package com.devforfun.sliidetask.di;

import com.devforfun.sliidetask.api.AuthenticationInterceptor
import com.devforfun.sliidetask.api.ServiceGenerator
import com.devforfun.sliidetask.repository.UsersRepository
import org.koin.dsl.module
import com.devforfun.sliidetask.services.*
import com.devforfun.sliidetask.services.impl.*


val networkModule = module {
    single { ServiceGenerator.createService(UsersProvider::class.java, "Bearer 44ef2a916d2ab85cfac3d42f63d9aac5a87cbe168944c477c29149ed9036daa8") }
}

val serviceModule = module {
    single<NetworkService> { NetworkServiceImpl(context = get()) }
    single<UsersService>{ UsersServiceImpl(get())}
}

val repositoryModule = module {
    single{ UsersRepository(usersService = get())}
}
