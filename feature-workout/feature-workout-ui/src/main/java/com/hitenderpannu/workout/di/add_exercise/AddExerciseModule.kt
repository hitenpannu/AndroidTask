package com.hitenderpannu.workout.di.add_exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hitenderpannu.common.utils.NetworkConnectionChecker
import com.hitenderpannu.workout.data.network.ExerciseApi
import com.hitenderpannu.workout.data.network.RemoteExerciseRepoImpl
import com.hitenderpannu.workout.di.AddExerciseScope
import com.hitenderpannu.workout.domain.ExerciseListInteractor
import com.hitenderpannu.workout.domain.ExerciseListInteractorImpl
import com.hitenderpannu.workout.domain.local_repo.LocalExerciseRepo
import com.hitenderpannu.workout.domain.remote_repo.RemoteExerciseRepo
import com.hitenderpannu.workout.ui.add_exercise.AddExerciseFragment
import com.hitenderpannu.workout.ui.add_exercise.AddExerciseFragmentViewModel
import com.hitenderpannu.workout.ui.add_exercise.AddExerciseListAdapter
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class AddExerciseModule(val fragment: AddExerciseFragment) {

    @AddExerciseScope
    @Provides
    fun provideAddExerciseFragmentViewModel(exerciseListInteractor: ExerciseListInteractor): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(AddExerciseFragmentViewModel::class.java)) {
                    return AddExerciseFragmentViewModel(exerciseListInteractor) as T
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
