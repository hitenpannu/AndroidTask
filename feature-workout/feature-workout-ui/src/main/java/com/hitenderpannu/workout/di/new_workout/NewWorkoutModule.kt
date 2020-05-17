package com.hitenderpannu.workout.di.new_workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hitenderpannu.workout.di.NewWorkoutScope
import com.hitenderpannu.workout.domain.WorkoutInteractor
import com.hitenderpannu.workout.ui.new_workout.NewWorkoutAdapter
import com.hitenderpannu.workout.ui.new_workout.NewWorkoutFragment
import com.hitenderpannu.workout.ui.new_workout.NewWorkoutFragmentViewModel
import dagger.Module
import dagger.Provides

@Module
class NewWorkoutModule(private val fragment: NewWorkoutFragment) {

    @NewWorkoutScope
    @Provides
    fun provideNewWorkoutAdapter(): NewWorkoutAdapter {
        return NewWorkoutAdapter()
    }

    @NewWorkoutScope
    @Provides
    fun provideViewModelFactory(workoutInteractor: WorkoutInteractor): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(NewWorkoutFragmentViewModel::class.java))
                    return NewWorkoutFragmentViewModel(workoutInteractor) as T

                throw IllegalArgumentException("UnSupported ViewModel requested")
            }
        }
    }
}
