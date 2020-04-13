package com.hitenderpannu.auth.ui.di

import com.hitenderpannu.auth.ui.AuthActivity
import com.hitenderpannu.auth.ui.di.modules.AuthModule
import com.hitenderpannu.auth.ui.login.LoginFragment
import com.hitenderpannu.auth.ui.signup.SignupFragment
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

    fun inject(loginFragment: LoginFragment)

    fun inject(signupFragment: SignupFragment)
}
