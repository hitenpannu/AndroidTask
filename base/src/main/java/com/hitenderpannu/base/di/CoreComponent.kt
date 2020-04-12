package com.hitenderpannu.base.di

import com.hitenderpannu.base.MainApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [CoreModule::class]
)
interface CoreComponent : ExposeProviders {
    fun inject(application: MainApplication)
}
