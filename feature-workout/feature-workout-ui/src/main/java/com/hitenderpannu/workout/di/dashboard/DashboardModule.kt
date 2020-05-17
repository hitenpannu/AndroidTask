package com.hitenderpannu.workout.di.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hitenderpannu.workout.di.DashboardScope
import com.hitenderpannu.workout.domain.WorkoutInteractor
import com.hitenderpannu.workout.ui.dashboard.DashboardFragmentViewModel
import dagger.Module
import dagger.Provides

@Module
class DashboardModule {

    @DashboardScope
    @Provides
    fun provideViewModelFactory(workoutInteractor: WorkoutInteractor): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(DashboardFragmentViewModel::class.java)) {
                    return DashboardFragmentViewModel(workoutInteractor) as T
                }
                throw IllegalArgumentException("Unexpected ViewModel Type")
            }
        }
    }
}
