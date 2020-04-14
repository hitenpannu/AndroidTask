package com.hitenderpannu.task.ui.di

import com.hitenderpannu.base.di.CoreComponent
import com.hitenderpannu.task.ui.TaskActivity
import com.hitenderpannu.task.ui.di.modules.CommonModule
import com.hitenderpannu.task.ui.di.modules.TaskModule
import dagger.Component

@TaskActivityScope
@Component(
    modules = [TaskModule::class, CommonModule::class],
    dependencies = [CoreComponent::class]
)
interface TaskComponent {
    fun inject(activity: TaskActivity)
}
