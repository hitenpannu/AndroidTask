package com.hitenderpannu.taskapp.di

import com.hitenderpannu.base.di.CoreComponent
import com.hitenderpannu.taskapp.home.HomeActivity
import com.hitenderpannu.taskapp.launcher.LauncherActivity
import dagger.Component

@ActivityScope
@Component(
    modules = [ActivityModule::class],
    dependencies = [CoreComponent::class]
)
interface ActivityComponent {

    fun inject(launcherActivity: LauncherActivity)

    fun inject(homeActivity: HomeActivity)
}
