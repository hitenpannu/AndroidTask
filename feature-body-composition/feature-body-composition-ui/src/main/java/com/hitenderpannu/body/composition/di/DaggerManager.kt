package com.hitenderpannu.body.composition.di

import android.content.Context
import com.hitenderpannu.base.MainApplication
import com.hitenderpannu.body.composition.di.header.BodyCompositionHeaderModule
import com.hitenderpannu.body.composition.di.header.DaggerBodyCompositionHeaderComponent
import com.hitenderpannu.body.composition.di.landing.BodyCompositionLandingModule
import com.hitenderpannu.body.composition.di.landing.DaggerBodyCompositionLandingComponent
import com.hitenderpannu.body.composition.ui.landing.LandingFragment
import com.hitenderpannu.body.composition.ui.landing_header.LandingHeaderFragment

internal object DaggerManager {

    private var bodyCompositionComponent: BodyCompositionComponent? = null

    fun inject(landingFragment: LandingFragment) {
        initializeCompositionComponent(landingFragment.requireContext().applicationContext)
        DaggerBodyCompositionLandingComponent
            .builder()
            .bodyCompositionComponent(bodyCompositionComponent)
            .bodyCompositionLandingModule(BodyCompositionLandingModule(landingFragment))
            .build()
            .inject(landingFragment)
    }

    fun inject(landingHeaderFragment: LandingHeaderFragment) {
        initializeCompositionComponent(landingHeaderFragment.requireContext().applicationContext)
        DaggerBodyCompositionHeaderComponent
            .builder()
            .bodyCompositionComponent(bodyCompositionComponent)
            .bodyCompositionHeaderModule(BodyCompositionHeaderModule(landingHeaderFragment))
            .build()
            .inject(landingHeaderFragment)
    }

    @Synchronized
    fun initializeCompositionComponent(applicationContext: Context) {
        if (bodyCompositionComponent == null) {
            bodyCompositionComponent = DaggerBodyCompositionComponent.builder()
                .coreComponent((applicationContext as MainApplication).coreComponent)
                .bodyCompositionModule(BodyCompositionModule(applicationContext))
                .build()
        }
    }
}
