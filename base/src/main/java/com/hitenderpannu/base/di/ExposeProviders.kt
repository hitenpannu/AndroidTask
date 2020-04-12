package com.hitenderpannu.base.di

import com.hitenderpannu.common.domain.UserPreferences
import com.hitenderpannu.common.utils.NetworkConnectionChecker
import retrofit2.Retrofit

interface ExposeProviders {

    fun provideRetrofit(): Retrofit

    fun provideNetworkConnectionChecker(): NetworkConnectionChecker

    fun provideUserPreferences(): UserPreferences
}
