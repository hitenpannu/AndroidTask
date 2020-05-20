package com.hitenderpannu.task.ui.di

import com.hitenderpannu.base.MainApplication
import com.hitenderpannu.task.ui.di.modules.TaskFragmentModule
import com.hitenderpannu.task.ui.taskFragment.TaskFragment

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
