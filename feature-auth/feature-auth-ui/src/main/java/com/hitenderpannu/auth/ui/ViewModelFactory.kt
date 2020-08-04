package com.hitenderpannu.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hitenderpannu.auth.domain.AuthProcessInteractor
import com.hitenderpannu.auth.domain.authform.AuthFormInteractor
import com.hitenderpannu.auth.ui.authform.AuthFragmentViewModel

class ViewModelFactory(
    private val authProcessInteractor: AuthProcessInteractor,
    private val authFormInteractor: AuthFormInteractor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthFragmentViewModel::class.java) ->
                AuthFragmentViewModel(authFormInteractor) as T
            modelClass.isAssignableFrom(AuthViewModel::class.java) ->
                AuthViewModel(authProcessInteractor) as T
            else -> throw Exception("Un Supported ViewMode")
        }
    }
}
