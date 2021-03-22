package com.devforfun.sliidetask.di;

import com.devforfun.sliidetask.api.ServiceGenerator
import com.devforfun.sliidetask.repository.UsersRepository
import com.devforfun.sliidetask.services.NetworkService
import com.devforfun.sliidetask.services.UsersProvider
import com.devforfun.sliidetask.services.UsersService
import com.devforfun.sliidetask.services.impl.NetworkServiceImpl
import com.devforfun.sliidetask.services.impl.UsersServiceImpl
import com.devforfun.sliidetask.utils.BaseSchedulerProvider
import com.devforfun.sliidetask.utils.SchedulerProvider
import org.koin.dsl.module


val networkModule = module {
    single { ServiceGenerator.createService(UsersProvider::class.java, "Bearer 44ef2a916d2ab85cfac3d42f63d9aac5a87cbe168944c477c29149ed9036daa8") }
}

val serviceModule = module {
    single<NetworkService> { NetworkServiceImpl(context = get()) }
    single<UsersService>{ UsersServiceImpl(get())}
    single<BaseSchedulerProvider> {SchedulerProvider()}
}



val repositoryModule = module {
    single{ UsersRepository(usersService = get())}
}
