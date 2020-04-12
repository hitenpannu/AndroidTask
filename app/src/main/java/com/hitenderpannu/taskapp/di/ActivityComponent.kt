package com.hitenderpannu.taskapp.di

import com.hitenderpannu.base.di.CoreComponent
import com.hitenderpannu.taskapp.MainActivity
import dagger.Component

@ActivityScope
@Component(
    modules = [ActivityModule::class],
    dependencies = [CoreComponent::class]
)
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)
}
