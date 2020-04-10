package com.hitenderpannu.base

import android.app.Application
import com.google.android.play.core.splitcompat.SplitCompat
import com.hitenderpannu.base.di.CoreModule
import com.hitenderpannu.base.di.DaggerCoreComponent

class MainApplication : Application() {

    val coreComponent by lazy {
        DaggerCoreComponent.builder()
            .coreModule(CoreModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        coreComponent.inject(this)

        SplitCompat.install(this)
    }
}
