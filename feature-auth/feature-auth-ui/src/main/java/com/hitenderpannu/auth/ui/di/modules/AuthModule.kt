package com.hitenderpannu.auth.ui.di.modules

import com.hitenderpannu.auth.data.network.AuthApi
import com.hitenderpannu.auth.data.network.AuthRepo
import com.hitenderpannu.auth.data.network.AuthRepoImpl
import com.hitenderpannu.auth.domain.login.LoginInteractor
import com.hitenderpannu.auth.domain.login.LoginInteractorImpl
import com.hitenderpannu.auth.domain.signup.SignUpInteractor
import com.hitenderpannu.auth.domain.signup.SignUpInteractorImpl
import com.hitenderpannu.auth.ui.AuthActivity
import com.hitenderpannu.auth.ui.ViewModelFactory
import com.hitenderpannu.auth.ui.di.AuthScope
import com.hitenderpannu.common.domain.UserPreferences
import com.hitenderpannu.common.utils.NetworkConnectionChecker
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class AuthModule(private val authActivity: AuthActivity) {

    @AuthScope
    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @AuthScope
    @Provides
    fun provideAuthRepo(authApi: AuthApi): AuthRepo = AuthRepoImpl(authApi)

    @AuthScope
    @Provides
    fun provideLoginInteractor(
        networkConnectionChecker: NetworkConnectionChecker,
        authRepo: AuthRepo,
        userPreferences: UserPreferences
    ): LoginInteractor = LoginInteractorImpl(networkConnectionChecker, authRepo, userPreferences)

    @Provides
    @AuthScope
    fun provideSignupInteractor(
        networkConnectionChecker: NetworkConnectionChecker,
        authRepo: AuthRepo,
        userPreferences: UserPreferences
    ): SignUpInteractor = SignUpInteractorImpl(networkConnectionChecker, authRepo, userPreferences)

    @Provides
    fun provideViewModelFactory(
        loginInteractor: LoginInteractor,
        signUpInteractor: SignUpInteractor
    ) = ViewModelFactory(loginInteractor, signUpInteractor)
}
