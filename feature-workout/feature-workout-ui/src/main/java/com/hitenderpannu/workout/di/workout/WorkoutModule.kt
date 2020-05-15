package com.hitenderpannu.workout.di.workout

import android.content.Context
import com.hitenderpannu.workout.data.local.DatabaseManager
import com.hitenderpannu.workout.data.local.repo.LocalExerciseRepoImpl
import com.hitenderpannu.workout.di.WorkoutScope
import com.hitenderpannu.workout.domain.local_repo.LocalExerciseRepo
import dagger.Module
import dagger.Provides

@Module
class WorkoutModule(private val applicationContext: Context) {

    @WorkoutScope
    @Provides
    fun provideDatabaseManager() = DatabaseManager(applicationContext)

    @WorkoutScope
    @Provides
    fun provideLocalExerciseRepo(databaseManager: DatabaseManager): LocalExerciseRepo {
        return LocalExerciseRepoImpl(
            databaseManager.provideExerciseDao(),
            databaseManager.provideBodyPartsDao(),
            databaseManager.provideEquipmentsDao()
        )
    }
}
