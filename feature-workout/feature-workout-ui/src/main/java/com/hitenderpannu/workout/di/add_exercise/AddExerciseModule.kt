package com.hitenderpannu.workout.di.add_exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hitenderpannu.workout.di.AddExerciseScope
import com.hitenderpannu.workout.domain.ExerciseListInteractor
import com.hitenderpannu.workout.domain.WorkoutInteractor
import com.hitenderpannu.workout.ui.add_exercise.AddExerciseFragment
import com.hitenderpannu.workout.ui.add_exercise.AddExerciseFragmentViewModel
import com.hitenderpannu.workout.ui.add_exercise.AddExerciseListAdapter
import dagger.Module
import dagger.Provides

@Module
class AddExerciseModule(val fragment: AddExerciseFragment) {

    @AddExerciseScope
    @Provides
    fun provideAddExerciseFragmentViewModel(
        exerciseListInteractor: ExerciseListInteractor,
        workoutInteractor: WorkoutInteractor
    ): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(AddExerciseFragmentViewModel::class.java)) {
                    return AddExerciseFragmentViewModel(exerciseListInteractor, workoutInteractor) as T
                }
                throw IllegalArgumentException("UnsupportedViewModel")
            }
        }
    }

    @AddExerciseScope
    @Provides
    fun provideAddExerciseListAdapter(): AddExerciseListAdapter {
        return AddExerciseListAdapter(listOf())
    }
}
