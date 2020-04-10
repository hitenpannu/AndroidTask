package com.hitenderpannu.userlist.ui.di

import com.hitenderpannu.base.MainApplication
import com.hitenderpannu.userlist.ui.UserListActivity

internal object DaggerManager {

    private var component: UserListComponent? = null

    fun inject(activity: UserListActivity) {
        component = DaggerUserListComponent.builder()
            .userListModule(UserListModule(activity))
            .coreComponent((activity.application as MainApplication).coreComponent)
            .build()
        component?.inject(activity)
    }
}
