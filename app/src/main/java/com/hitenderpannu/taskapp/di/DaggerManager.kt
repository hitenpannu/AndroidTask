package com.hitenderpannu.taskapp.di

import com.hitenderpannu.base.MainApplication
import com.hitenderpannu.taskapp.MainActivity

internal object DaggerManager {

    private var activityComponent: ActivityComponent? = null

    fun inject(activity: MainActivity) {
        val coreComponent = (activity.application as MainApplication).coreComponent
        activityComponent = DaggerActivityComponent.builder()
            .coreComponent(coreComponent)
            .activityModule(ActivityModule(activity))
            .build()

        activityComponent?.inject(activity)
    }
}
