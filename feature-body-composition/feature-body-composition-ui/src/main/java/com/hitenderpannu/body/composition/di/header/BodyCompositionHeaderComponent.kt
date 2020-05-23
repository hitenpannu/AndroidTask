package com.hitenderpannu.body.composition.di.header

import com.hitenderpannu.body.composition.di.BodyCompositionComponent
import com.hitenderpannu.body.composition.ui.landing_header.LandingHeaderFragment
import dagger.Component

@BodyCompositionHeaderScope
@Component(
    modules = [BodyCompositionHeaderModule::class],
    dependencies = [BodyCompositionComponent::class]
)
interface BodyCompositionHeaderComponent {
    fun inject(landingHeaderFragment: LandingHeaderFragment)
}
