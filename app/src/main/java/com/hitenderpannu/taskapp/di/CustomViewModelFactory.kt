package com.hitenderpannu.taskapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hitenderpannu.auth.ui.AuthRouter
import com.hitenderpannu.common.domain.UserPreferences
import com.hitenderpannu.taskapp.MainActivityViewModel
import java.lang.Exception

class CustomViewModelFactory(
    private val userPreferences: UserPreferences,
    private val authRouter: AuthRouter
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(userPreferences, authRouter) as T
        } else {
            throw Exception("Un supported")
        }
    }
}
