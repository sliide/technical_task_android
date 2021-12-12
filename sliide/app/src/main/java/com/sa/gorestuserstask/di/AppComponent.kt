package com.sa.gorestuserstask.di

import android.app.Application
import com.sa.gorestuserstask.di.module.AppModule
import com.sa.gorestuserstask.di.module.NetworkModule
import com.sa.gorestuserstask.ui.users.UserListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(fragment: UserListFragment)
}
