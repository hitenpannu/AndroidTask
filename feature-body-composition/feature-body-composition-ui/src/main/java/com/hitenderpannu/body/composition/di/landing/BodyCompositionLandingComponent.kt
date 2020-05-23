package com.hitenderpannu.body.composition.di.landing

import com.hitenderpannu.body.composition.di.BodyCompositionComponent
import com.hitenderpannu.body.composition.ui.landing.LandingFragment
import dagger.Component

@BodyCompositionLandingScope
@Component(
    modules = [BodyCompositionLandingModule::class],
    dependencies = [BodyCompositionComponent::class]
)
internal interface BodyCompositionLandingComponent {
    fun inject(landingFragment: LandingFragment)
}
