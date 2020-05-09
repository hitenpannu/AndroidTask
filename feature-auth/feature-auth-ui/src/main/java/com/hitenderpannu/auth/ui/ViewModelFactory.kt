package com.hitenderpannu.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hitenderpannu.auth.domain.login.LoginInteractor
import com.hitenderpannu.auth.domain.signup.SignUpInteractor
import com.hitenderpannu.auth.ui.login.AuthFragmentViewModel

class ViewModelFactory(
    private val loginInteractor: LoginInteractor,
    private val signUpInteractor: SignUpInteractor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthFragmentViewModel::class.java) ->
                AuthFragmentViewModel(loginInteractor, signUpInteractor) as T
            modelClass.isAssignableFrom(AuthViewModel::class.java) ->
                AuthViewModel() as T
            else -> throw Exception("Un Supported ViewMode")
        }
    }
}
