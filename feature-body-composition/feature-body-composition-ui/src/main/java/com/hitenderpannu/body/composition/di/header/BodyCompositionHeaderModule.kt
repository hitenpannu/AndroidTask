package com.hitenderpannu.body.composition.di.header

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hitenderpannu.body.composition.domain.BodyCompositionInteractor
import com.hitenderpannu.body.composition.ui.landing_header.LandingHeaderFragment
import com.hitenderpannu.body.composition.ui.landing_header.LandingHeaderFragmentViewModel
import dagger.Module
import dagger.Provides

@Module
class BodyCompositionHeaderModule(private val landingHeaderFragment: LandingHeaderFragment) {

    @BodyCompositionHeaderScope
    @Provides
    fun provideViewModelFactory(bodyCompositionInteractor: BodyCompositionInteractor): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(LandingHeaderFragmentViewModel::class.java)) {
                    return LandingHeaderFragmentViewModel(bodyCompositionInteractor) as T
                }
                throw IllegalArgumentException("Unexpected View Model requested")
            }
        }
    }
}
