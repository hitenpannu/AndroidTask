package com.hitenderpannu.base.di

import com.hitenderpannu.base.MainApplication
import com.hitenderpannu.common.utils.NetworkConnectionChecker
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(
    modules = [CoreModule::class]
)
interface CoreComponent {

    fun inject(application: MainApplication)

    fun provideRetrofit(): Retrofit

    fun provideNetworkConnectionChecker(): NetworkConnectionChecker
}
