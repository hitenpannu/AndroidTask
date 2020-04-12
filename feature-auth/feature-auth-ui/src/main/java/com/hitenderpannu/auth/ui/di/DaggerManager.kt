package com.hitenderpannu.auth.ui.di

import com.hitenderpannu.auth.ui.AuthActivity
import com.hitenderpannu.auth.ui.LoginFragment
import com.hitenderpannu.auth.ui.SignupFragment
import com.hitenderpannu.base.MainApplication

internal object DaggerManager {

    private var authComponent: AuthComponent? = null

    fun inject(authActivity: AuthActivity) {
        val coreComponent = (authActivity.application as MainApplication).coreComponent
        authComponent = DaggerAuthComponent.builder()
            .coreComponent(coreComponent)
            .build()
        authComponent?.inject(authActivity)
    }

    fun inject(loginFragment: LoginFragment) {
        authComponent?.inject(loginFragment)
    }

    fun inject(signupFragment: SignupFragment) {
        authComponent?.inject(signupFragment)
    }

    fun destroy() {
        authComponent = null
    }
}
