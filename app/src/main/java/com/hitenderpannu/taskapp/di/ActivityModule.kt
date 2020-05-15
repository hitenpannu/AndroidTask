package com.hitenderpannu.taskapp.di

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.hitenderpannu.auth.ui.AuthRouter
import com.hitenderpannu.common.domain.UserPreferences
import com.hitenderpannu.taskapp.DynamicFeatureManager
import com.hitenderpannu.taskapp.home.HomeRouter
import com.hitenderpannu.taskapp.launcher.LauncherViewModel
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @ActivityScope
    @Provides
    fun provideAuthRouter() = AuthRouter(activity)

    @ActivityScope
    @Provides
    fun provideHomeRouter() = HomeRouter(activity)


    @ActivityScope
    @Provides
    fun provideDynamicFeatureManager() = DynamicFeatureManager(activity)

    @ActivityScope
    @Provides
    fun provideViewModelFactory(
        userPreferences: UserPreferences,
        authRouter: AuthRouter,
        homeRouter: HomeRouter,
        dynamicFeatureManager: DynamicFeatureManager) =
        CustomViewModelFactory(userPreferences, authRouter, homeRouter, dynamicFeatureManager)

    @Provides
    fun provideMainActivityViewModel(
        viewModelFactory: CustomViewModelFactory
    ): LauncherViewModel {
        return ViewModelProviders.of(activity, viewModelFactory).get(LauncherViewModel::class.java)
    }
}
