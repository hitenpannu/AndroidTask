package com.hitenderpannu.workout.di.dashboard

import com.hitenderpannu.workout.di.DashboardScope
import com.hitenderpannu.workout.di.workout.WorkoutComponent
import com.hitenderpannu.workout.ui.dashboard.DashBoardFragment
import dagger.Component

@DashboardScope
@Component(
    modules = [DashboardModule::class],
    dependencies = [WorkoutComponent::class]
)
interface DashboardComponent {
    fun inject(dashBoardFragment: DashBoardFragment)
}
