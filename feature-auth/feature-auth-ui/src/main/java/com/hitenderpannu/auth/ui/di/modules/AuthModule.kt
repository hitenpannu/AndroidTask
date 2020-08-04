package com.hitenderpannu.auth.ui.di.modules

import com.hitenderpannu.auth.data.network.AuthApi
import com.hitenderpannu.auth.data.network.AuthRepo
import com.hitenderpannu.auth.data.network.AuthRepoImpl
import com.hitenderpannu.auth.domain.AuthProcessInteractor
import com.hitenderpannu.auth.domain.AuthProcessInteractorImpl
import com.hitenderpannu.auth.domain.authform.AuthFormInteractor
import com.hitenderpannu.auth.domain.authform.AuthFormInteractorImpl
import com.hitenderpannu.auth.domain.guest.GuestLoginUseCase
import com.hitenderpannu.auth.domain.guest.GuestLoginUseCaseImpl
import com.hitenderpannu.auth.domain.login.LoginUseCase
import com.hitenderpannu.auth.domain.login.LoginUseCaseImpl
import com.hitenderpannu.auth.domain.signup.SignUpUseCase
import com.hitenderpannu.auth.domain.signup.SignUpUseCaseImpl
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
    fun provideGuestLoginUseCase(userPreferences: UserPreferences): GuestLoginUseCase {
        return GuestLoginUseCaseImpl(userPreferences)
    }

    @AuthScope
    @Provides
    fun provideLoginUseCase(
        networkConnectionChecker: NetworkConnectionChecker,
        authRepo: AuthRepo,
        userPreferences: UserPreferences
    ): LoginUseCase = LoginUseCaseImpl(networkConnectionChecker, authRepo, userPreferences)

    @Provides
    @AuthScope
    fun provideSignUpUseCase(
        networkConnectionChecker: NetworkConnectionChecker,
        authRepo: AuthRepo,
        userPreferences: UserPreferences
    ): SignUpUseCase = SignUpUseCaseImpl(networkConnectionChecker, authRepo, userPreferences)

    @Provides
    @AuthScope
    fun provideAuthInteractor(
        guestLoginUseCase: GuestLoginUseCase,
        loginUseCase: LoginUseCase,
        signUpUseCase: SignUpUseCase
    ): AuthProcessInteractor {
        return AuthProcessInteractorImpl(guestLoginUseCase, loginUseCase, signUpUseCase)
    }

    @Provides
    @AuthScope
    fun provideAuthFormInteractor(authProcessInteractor: AuthProcessInteractor): AuthFormInteractor {
        return AuthFormInteractorImpl(authProcessInteractor)
    }

    @Provides
    fun provideViewModelFactory(
        authProcessInteractor: AuthProcessInteractor,
        authFormInteractor: AuthFormInteractor
    ) = ViewModelFactory(authProcessInteractor, authFormInteractor)
}
