package com.sliide.di.users.list

import com.sliide.di.app.AppProvider
import dagger.Component

@Component(dependencies = [AppProvider::class], modules = [UserListModule::class])
@UserListScope
interface UserListComponent : UserListProvider