package com.hitenderpannu.taskapp

import com.hitenderpannu.taskapp.di.AppComponent
import com.hitenderpannu.taskapp.di.AppModule
import com.hitenderpannu.taskapp.di.DaggerAppComponent

class MainApplication : ComponentBuilder() {

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build().also { it.inject(this) }
    }

    override fun getApplicationComponent(): AppComponent = appComponent

    override fun onCreate() {
        super.onCreate()
    }
}
