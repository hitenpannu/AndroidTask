package com.hitenderpannu.auth.ui.di

import com.hitenderpannu.auth.ui.AuthActivity
import com.hitenderpannu.auth.ui.di.modules.AuthModule
import com.hitenderpannu.auth.ui.authform.AuthFragment
import com.hitenderpannu.base.MainApplication

internal object DaggerManager {

    private var authComponent: AuthComponent? = null

    fun inject(authActivity: AuthActivity) {
        val coreComponent = (authActivity.application as MainApplication).coreComponent
        authComponent = DaggerAuthComponent.builder().coreComponent(coreComponent)
            .authModule(AuthModule(authActivity))
            .build()
        authComponent?.inject(authActivity)
    }

    fun inject(authFragment: AuthFragment) {
        authComponent?.inject(authFragment)
    }

    fun destroy() {
        authComponent = null
    }
}
