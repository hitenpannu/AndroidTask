package com.hitenderpannu.workout.di.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hitenderpannu.workout.di.ExerciseFilterScope
import com.hitenderpannu.workout.domain.ExerciseListInteractor
import com.hitenderpannu.workout.ui.exercise_filters.ExerciseFilterFragmentViewModel
import com.hitenderpannu.workout.ui.exercise_filters.ExerciseFiltersFragment
import dagger.Module
import dagger.Provides

@Module
class ExerciseFilterFragmentViewModule(private val fragment: ExerciseFiltersFragment) {

    @ExerciseFilterScope
    @Provides
    fun provideViewModelFactory(exerciseListInteractor: ExerciseListInteractor): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(ExerciseFilterFragmentViewModel::class.java)) {
                    return ExerciseFilterFragmentViewModel(exerciseListInteractor) as T
                }
                throw IllegalArgumentException("Unsupported ViewModel type")
            }
        }
    }
}
