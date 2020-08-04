package com.hitenderpannu.auth.ui.di

import com.hitenderpannu.auth.ui.AuthActivity
import com.hitenderpannu.auth.ui.di.modules.AuthModule
import com.hitenderpannu.auth.ui.authform.AuthFragment
import com.hitenderpannu.base.di.CoreComponent
import dagger.Component

@AuthScope
@Component(
    modules = [
        AuthModule::class],
    dependencies = [
        CoreComponent::class
    ]
)
interface AuthComponent {

    fun inject(authActivity: AuthActivity)

    fun inject(authFragment: AuthFragment)
}
