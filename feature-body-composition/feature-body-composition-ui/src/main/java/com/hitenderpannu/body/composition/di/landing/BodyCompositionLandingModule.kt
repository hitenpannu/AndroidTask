package com.hitenderpannu.body.composition.di.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hitenderpannu.body.composition.domain.BodyCompositionInteractor
import com.hitenderpannu.body.composition.ui.landing.LandingEntriesAdapter
import com.hitenderpannu.body.composition.ui.landing.LandingFragment
import com.hitenderpannu.body.composition.ui.landing.LandingFragmentViewModel
import dagger.Module
import dagger.Provides

@Module
internal class BodyCompositionLandingModule(landingFragment: LandingFragment) {

    @BodyCompositionLandingScope
    @Provides
    fun provideLandingAdapter(): LandingEntriesAdapter{
        return LandingEntriesAdapter()
    }

    @BodyCompositionLandingScope
    @Provides
    fun provideViewModelFactory(bodyCompositionInteractor: BodyCompositionInteractor): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(LandingFragmentViewModel::class.java)) {
                    return LandingFragmentViewModel(bodyCompositionInteractor) as T
                }
                throw IllegalArgumentException("Unexpected View Model")
            }
        }
    }
}
