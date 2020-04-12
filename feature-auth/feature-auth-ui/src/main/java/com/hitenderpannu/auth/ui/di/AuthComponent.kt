package com.hitenderpannu.auth.ui.di

import com.hitenderpannu.auth.ui.AuthActivity
import com.hitenderpannu.auth.ui.LoginFragment
import com.hitenderpannu.auth.ui.SignupFragment
import com.hitenderpannu.auth.ui.di.modules.AuthModule
import com.hitenderpannu.auth.ui.di.modules.LoginModule
import com.hitenderpannu.auth.ui.di.modules.SignupModule
import com.hitenderpannu.base.di.CoreComponent
import dagger.Component

@AuthScope
@Component(
    modules = [
        AuthModule::class,
        LoginModule::class,
        SignupModule::class
    ],
    dependencies = [
        CoreComponent::class
    ]
)
interface AuthComponent {

    fun inject(authActivity: AuthActivity)

    fun inject(loginFragment: LoginFragment)

    fun inject(signupFragment: SignupFragment)
}
