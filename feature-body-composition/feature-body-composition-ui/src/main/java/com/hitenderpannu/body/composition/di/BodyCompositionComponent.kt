package com.hitenderpannu.body.composition.di

import com.hitenderpannu.base.di.CoreComponent
import com.hitenderpannu.body.composition.domain.BodyCompositionInteractor
import dagger.Component

@BodyCompositionScope
@Component(
    modules = [BodyCompositionModule::class],
    dependencies = [CoreComponent::class]
)
interface BodyCompositionComponent {

    fun provideBodyCompositionInteractor(): BodyCompositionInteractor
}
