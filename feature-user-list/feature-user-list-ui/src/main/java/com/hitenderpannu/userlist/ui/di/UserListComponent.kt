package com.hitenderpannu.userlist.ui.di

import com.hitenderpannu.base.di.CoreComponent
import com.hitenderpannu.userlist.ui.UserListActivity
import dagger.Component

@FeatureScope
@Component(
    modules = [UserListModule::class],
    dependencies = [CoreComponent::class]
)
interface UserListComponent {
    fun inject(activity: UserListActivity)
}
