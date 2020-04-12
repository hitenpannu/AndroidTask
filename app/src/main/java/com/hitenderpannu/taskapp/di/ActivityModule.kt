package com.hitenderpannu.taskapp.di

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.hitenderpannu.auth.ui.AuthRouter
import com.hitenderpannu.common.domain.UserPreferences
import com.hitenderpannu.taskapp.MainActivityViewModel
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @ActivityScope
    @Provides
    fun provideAuthRouter() = AuthRouter(activity)

    @ActivityScope
    @Provides
    fun provideViewModelFactory(userPreferences: UserPreferences, authRouter: AuthRouter) = CustomViewModelFactory(userPreferences, authRouter)

    @Provides
    fun provideMainActivityViewModel(
        viewModelFactory: CustomViewModelFactory
    ): MainActivityViewModel {
        return ViewModelProviders.of(activity, viewModelFactory).get(MainActivityViewModel::class.java)
    }
}
