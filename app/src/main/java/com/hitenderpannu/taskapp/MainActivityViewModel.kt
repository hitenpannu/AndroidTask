package com.hitenderpannu.taskapp

import androidx.lifecycle.ViewModel
import com.hitenderpannu.auth.ui.AuthRouter
import com.hitenderpannu.common.domain.UserPreferences

class MainActivityViewModel(
    private val userPreferences: UserPreferences,
    private val authRouter: AuthRouter
) : ViewModel() {
    init {
        checkIfUserIsLoggedIn()
    }

    private fun checkIfUserIsLoggedIn() {
        if (!userPreferences.isUserLoggedIn()) {
            authRouter.startAuth()
        }
    }
}
