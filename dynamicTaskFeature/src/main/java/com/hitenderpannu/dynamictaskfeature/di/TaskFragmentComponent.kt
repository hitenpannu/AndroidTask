package com.hitenderpannu.dynamictaskfeature.di

import com.hitenderpannu.base.di.CoreComponent
import com.hitenderpannu.dynamictaskfeature.di.TaskFragmentScope
import com.hitenderpannu.dynamictaskfeature.di.modules.TaskFragmentModule
import com.hitenderpannu.dynamictaskfeature.ui.TaskFragment
import dagger.Component

@TaskFragmentScope
@Component(
    modules = [TaskFragmentModule::class],
    dependencies = [CoreComponent::class]
)
interface TaskFragmentComponent {

    fun inject(fragment: TaskFragment)
}
