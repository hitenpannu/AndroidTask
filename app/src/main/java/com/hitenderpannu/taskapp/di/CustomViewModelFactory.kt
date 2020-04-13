package com.hitenderpannu.taskapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hitenderpannu.auth.ui.AuthRouter
import com.hitenderpannu.common.domain.UserPreferences
import com.hitenderpannu.taskapp.home.HomeRouter
import com.hitenderpannu.taskapp.home.HomeViewModel
import com.hitenderpannu.taskapp.launcher.LauncherViewModel

class CustomViewModelFactory(
    private val userPreferences: UserPreferences,
    private val authRouter: AuthRouter,
    private val homeRouter: HomeRouter
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LauncherViewModel::class.java) -> {
                LauncherViewModel(userPreferences, authRouter, homeRouter) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(userPreferences) as T
            }
            else -> {
                throw Exception("Un supported")
            }
        }
    }
}
