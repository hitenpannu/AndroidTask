package com.hitenderpannu.workout.di.workout

import android.content.Context
import com.hitenderpannu.common.utils.NetworkConnectionChecker
import com.hitenderpannu.workout.data.local.DatabaseManager
import com.hitenderpannu.workout.data.local.dao.BodyPartsDao
import com.hitenderpannu.workout.data.local.dao.EquipmentsDao
import com.hitenderpannu.workout.data.local.dao.ExerciseDao
import com.hitenderpannu.workout.data.local.dao.WorkoutDao
import com.hitenderpannu.workout.data.local.repo.LocalBodyPartsRepoImpl
import com.hitenderpannu.workout.data.local.repo.LocalEquipmentsRepoImpl
import com.hitenderpannu.workout.data.local.repo.LocalExerciseRepoImpl
import com.hitenderpannu.workout.data.local.repo.LocalWorkoutRepoImpl
import com.hitenderpannu.workout.data.network.ExerciseApi
import com.hitenderpannu.workout.data.network.RemoteExerciseRepoImpl
import com.hitenderpannu.workout.di.WorkoutScope
import com.hitenderpannu.workout.domain.ExerciseListInteractor
import com.hitenderpannu.workout.domain.ExerciseListInteractorImpl
import com.hitenderpannu.workout.domain.WorkoutInteractor
import com.hitenderpannu.workout.domain.WorkoutInteractorImpl
import com.hitenderpannu.workout.domain.local_repo.LocalBodyPartsRepo
import com.hitenderpannu.workout.domain.local_repo.LocalEquipmentsRepo
import com.hitenderpannu.workout.domain.local_repo.LocalExerciseRepo
import com.hitenderpannu.workout.domain.local_repo.LocalWorkoutRepo
import com.hitenderpannu.workout.domain.remote_repo.RemoteExerciseRepo
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class WorkoutModule(private val applicationContext: Context) {

    @WorkoutScope
    @Provides
    fun provideExerciseDaoProvider(): ExerciseDao {
        return DatabaseManager.getDatabaseInstance(applicationContext).exerciseDao()
    }

    @WorkoutScope
    @Provides
    fun provideBodyPartsDaoProvider(): BodyPartsDao {
        return DatabaseManager.getDatabaseInstance(applicationContext).bodyPartsDao()
    }

    @WorkoutScope
    @Provides
    fun provideEquipmentsDaoProvider(): EquipmentsDao {
        return DatabaseManager.getDatabaseInstance(applicationContext).equipmentsDao()
    }

    @WorkoutScope
    @Provides
    fun provideWorkoutDaoProvider(): WorkoutDao {
        return DatabaseManager.getDatabaseInstance(applicationContext).workoutDao()
    }

    @WorkoutScope
    @Provides
    fun provideLocalExerciseRepo(
        exerciseDaoProvider: ExerciseDao,
        bodyPartsDaoProvider: BodyPartsDao,
        equipmentsDaoProvider: EquipmentsDao
    ): LocalExerciseRepo {
        return LocalExerciseRepoImpl(
            exerciseDaoProvider,
            bodyPartsDaoProvider,
            equipmentsDaoProvider
        )
    }

    @WorkoutScope
    @Provides
    fun provideLocalBodyPartsRepo(
        bodyPartsDaoProvider: BodyPartsDao
    ): LocalBodyPartsRepo {
        return LocalBodyPartsRepoImpl(bodyPartsDaoProvider)
    }

    @WorkoutScope
    @Provides
    fun provideLocalEquipmentsRepo(equipmentsDaoProvider: EquipmentsDao): LocalEquipmentsRepo {
        return LocalEquipmentsRepoImpl(equipmentsDaoProvider)
    }

    @WorkoutScope
    @Provides
    fun provideExerciseApi(retrofit: Retrofit) = retrofit.create(ExerciseApi::class.java)

    @WorkoutScope
    @Provides
    fun provideExerciseRemoteRepo(exerciseApi: ExerciseApi): RemoteExerciseRepo {
        return RemoteExerciseRepoImpl(exerciseApi)
    }

    @WorkoutScope
    @Provides
    fun provideWorkoutInteractor(
        localWorkoutRepo: LocalWorkoutRepo,
        exerciseRepo: LocalExerciseRepo
    ): WorkoutInteractor {
        return WorkoutInteractorImpl(localWorkoutRepo, exerciseRepo)
    }

    @WorkoutScope
    @Provides
    fun providerWorkoutRepo(
        exerciseDaoProvider: ExerciseDao,
        workoutDaoProvider: WorkoutDao
    ): LocalWorkoutRepo {
        return LocalWorkoutRepoImpl(
            workoutDaoProvider,
            exerciseDaoProvider
        )
    }

    @WorkoutScope
    @Provides
    fun provideExerciseListInteractor(
        networkConnectionChecker: NetworkConnectionChecker,
        remoteExerciseRepo: RemoteExerciseRepo,
        localExerciseRepo: LocalExerciseRepo,
        localBodyPartsRepo: LocalBodyPartsRepo,
        localEquipmentsRepo: LocalEquipmentsRepo
    ): ExerciseListInteractor {
        return ExerciseListInteractorImpl(
            networkConnectionChecker,
            remoteExerciseRepo,
            localExerciseRepo,
            localBodyPartsRepo, localEquipmentsRepo
        )
    }
}
