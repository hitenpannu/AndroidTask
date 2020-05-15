package com.hitenderpannu.feature_dashboard_ui.di

import com.hitenderpannu.base.di.CoreComponent
import com.hitenderpannu.feature_dashboard_ui.dashboard.DashBoardFragment
import dagger.Component

@DashboardScope
@Component(
    modules = [DashboardModule::class],
    dependencies = [CoreComponent::class]
)
interface DashboardComponent {
    fun inject(dashBoardFragment: DashBoardFragment)
}
