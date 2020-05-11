package com.hitenderpannu.taskapp.launcher

import androidx.lifecycle.ViewModel
import com.hitenderpannu.auth.ui.AuthRouter
import com.hitenderpannu.common.domain.UserPreferences
import com.hitenderpannu.feature_dashboard_ui.DashBoardRouter
import com.hitenderpannu.taskapp.home.HomeRouter

class LauncherViewModel(
    private val userPreferences: UserPreferences,
    private val authRouter: AuthRouter,
    private val homeRouter: HomeRouter,
    private val dashBoardRouter: DashBoardRouter
) : ViewModel() {
    init {
        checkIfUserIsLoggedIn()
    }

    fun checkIfUserIsLoggedIn() {
        if (!userPreferences.isUserLoggedIn()) {
            authRouter.startAuth()
        } else {
            dashBoardRouter.startDashBoard()
        }
    }
}
