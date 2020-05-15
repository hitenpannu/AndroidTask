package com.hitenderpannu.workout.di.add_exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hitenderpannu.common.utils.NetworkConnectionChecker
import com.hitenderpannu.workout.data.network.ExerciseApi
import com.hitenderpannu.workout.data.network.RemoteExerciseRepo
import com.hitenderpannu.workout.data.network.RemoteExerciseRepoImpl
import com.hitenderpannu.workout.di.AddExerciseScope
import com.hitenderpannu.workout.domain.ExerciseListInteractor
import com.hitenderpannu.workout.domain.ExerciseListInteractorImpl
import com.hitenderpannu.workout.ui.addExercise.AddExerciseFragment
import com.hitenderpannu.workout.ui.addExercise.AddExerciseFragmentViewModel
import com.hitenderpannu.workout.ui.addExercise.AddExerciseListAdapter
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class AddExerciseModule(val fragment: AddExerciseFragment) {

    @AddExerciseScope
    @Provides
    fun provideExerciseApi(retrofit: Retrofit) = retrofit.create(ExerciseApi::class.java)

    @AddExerciseScope
    @Provides
    fun provideExerciseRemoteRepo(exerciseApi: ExerciseApi): RemoteExerciseRepo {
        return RemoteExerciseRepoImpl(exerciseApi)
    }

    @AddExerciseScope
    @Provides
    fun provideExerciseListInteractor(
        networkConnectionChecker: NetworkConnectionChecker,
        remoteExerciseRepo: RemoteExerciseRepo
    ): ExerciseListInteractor {
        return ExerciseListInteractorImpl(networkConnectionChecker, remoteExerciseRepo)
    }

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
