package com.hitenderpannu.task.ui.di

import androidx.appcompat.app.AppCompatActivity
import com.hitenderpannu.base.MainApplication
import com.hitenderpannu.task.ui.di.modules.CommonModule
import com.hitenderpannu.task.ui.di.modules.TaskFragmentModule
import com.hitenderpannu.task.ui.taskFragment.TaskFragment

internal object DaggerManager {

    private var commonModule: CommonModule? = null

    fun inject(activity: AppCompatActivity) {
        val coreComponent = (activity.application as MainApplication).coreComponent
        if (commonModule == null) commonModule = CommonModule()
        DaggerTaskComponent.builder()
            .coreComponent(coreComponent)
            .commonModule(commonModule)
            .build()
    }

    fun inject(fragment: TaskFragment) {
        val coreComponent = (fragment.activity!!.application as MainApplication).coreComponent
        if (commonModule == null) commonModule = CommonModule()

        DaggerTaskFragmentComponent.builder()
            .coreComponent(coreComponent)
            .commonModule(commonModule)
            .taskFragmentModule(TaskFragmentModule(fragment))
            .build()
            .inject(fragment)
    }
}
