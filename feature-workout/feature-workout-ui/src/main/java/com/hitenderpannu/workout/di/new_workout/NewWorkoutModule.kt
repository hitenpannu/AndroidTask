package com.hitenderpannu.workout.di.new_workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hitenderpannu.workout.di.NewWorkoutScope
import com.hitenderpannu.workout.domain.WorkoutInteractor
import com.hitenderpannu.workout.ui.new_workout.NewWorkoutAdapter
import com.hitenderpannu.workout.ui.new_workout.NewWorkoutActivity
import com.hitenderpannu.workout.ui.new_workout.NewWorkoutActivityViewModel
import dagger.Module
import dagger.Provides

@Module
class NewWorkoutModule(private val activity: NewWorkoutActivity) {

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
                if (modelClass.isAssignableFrom(NewWorkoutActivityViewModel::class.java))
                    return NewWorkoutActivityViewModel(workoutInteractor) as T

                throw IllegalArgumentException("UnSupported ViewModel requested")
            }
        }
    }
}
