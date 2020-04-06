package com.hitenderpannu.taskapp

import android.app.Application
import com.hitenderpannu.taskapp.di.AppComponent
import com.hitenderpannu.userlist.ui.UserListFragment
import com.hitenderpannu.userlist.ui.di.UserListComponentProvider
import com.hitenderpannu.userlist.ui.di.UserListModule

abstract class ComponentBuilder : Application(), UserListComponentProvider {

    abstract fun getApplicationComponent(): AppComponent

    override fun inject(fragment: UserListFragment) {
        getApplicationComponent()
            .provideUserListComponent()
            .plus(UserListModule(fragment))
            .build()
            .inject(fragment)
    }
}
