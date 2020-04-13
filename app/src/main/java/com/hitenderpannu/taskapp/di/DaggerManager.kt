package com.hitenderpannu.taskapp.di

import com.hitenderpannu.base.MainApplication
import com.hitenderpannu.taskapp.home.HomeActivity
import com.hitenderpannu.taskapp.launcher.LauncherActivity

internal object DaggerManager {

    fun inject(activity: LauncherActivity) {
        val coreComponent = (activity.application as MainApplication).coreComponent
        DaggerActivityComponent.builder()
            .coreComponent(coreComponent)
            .activityModule(ActivityModule(activity))
            .build()
            .inject(activity)
    }

    fun inject(activity: HomeActivity) {
        val coreComponent = (activity.application as MainApplication).coreComponent
        DaggerActivityComponent.builder()
            .coreComponent(coreComponent)
            .activityModule(ActivityModule(activity))
            .build()
            .inject(activity)
    }
}
