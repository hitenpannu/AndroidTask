package com.hitenderpannu.task.ui.di

import com.hitenderpannu.base.di.CoreComponent
import com.hitenderpannu.task.ui.taskFragment.TaskFragment
import com.hitenderpannu.task.ui.di.modules.CommonModule
import com.hitenderpannu.task.ui.di.modules.TaskFragmentModule
import dagger.Component

@TaskFragmentScope
@Component(
    modules = [TaskFragmentModule::class, CommonModule::class],
    dependencies = [CoreComponent::class]
)
interface TaskFragmentComponent {

    fun inject(fragment: TaskFragment)
}
