package com.sliide.di.users.create

import com.sliide.di.app.AppProvider
import dagger.Component

@Component(dependencies = [AppProvider::class], modules = [CreateUserModule::class])
@CreateUserScope
interface CreateUserComponent : CreateUserProvider