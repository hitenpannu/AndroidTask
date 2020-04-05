package com.hitenderpannu.taskapp.di

import com.hitenderpannu.taskapp.MainApplication
import com.hitenderpannu.userlist.ui.di.UserListComponent
import dagger.Component

@Component(
    modules = [AppModule::class]
)
interface AppComponent {

    fun inject(application: MainApplication)

    fun provideUserListComponent(): UserListComponent.Builder

}
