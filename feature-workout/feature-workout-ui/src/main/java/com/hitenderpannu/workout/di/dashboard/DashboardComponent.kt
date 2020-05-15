package com.hitenderpannu.workout.di.dashboard

import com.hitenderpannu.base.di.CoreComponent
import com.hitenderpannu.workout.di.DashboardScope
import com.hitenderpannu.workout.ui.dashboard.DashBoardFragment
import dagger.Component

@DashboardScope
@Component(
    modules = [DashboardModule::class],
    dependencies = [CoreComponent::class]
)
interface DashboardComponent {
    fun inject(dashBoardFragment: DashBoardFragment)
}
