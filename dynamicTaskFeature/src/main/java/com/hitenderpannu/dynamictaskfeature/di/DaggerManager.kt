package com.hitenderpannu.dynamictaskfeature.di

import com.hitenderpannu.base.MainApplication
import com.hitenderpannu.dynamictaskfeature.di.modules.TaskFragmentModule
import com.hitenderpannu.dynamictaskfeature.ui.TaskFragment

internal object DaggerManager {

    fun inject(fragment: TaskFragment) {
        val coreComponent = (fragment.requireActivity().application as MainApplication).coreComponent
        DaggerTaskFragmentComponent.builder()
            .coreComponent(coreComponent)
            .taskFragmentModule(TaskFragmentModule(fragment))
            .build()
            .inject(fragment)
    }
}
