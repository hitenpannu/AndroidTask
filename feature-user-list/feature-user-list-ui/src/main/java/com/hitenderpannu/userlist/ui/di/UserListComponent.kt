package com.hitenderpannu.userlist.ui.di

import com.hitenderpannu.userlist.ui.UserListFragment
import dagger.Subcomponent

@FeatureScope
@Subcomponent(
    modules = [UserListModule::class]
)
interface UserListComponent {

    fun inject(fragment: UserListFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): UserListComponent
    }
}
