package com.hitenderpannu.workout.domain

import com.hitenderpannu.workout.domain.local_repo.LocalExerciseRepo
import com.hitenderpannu.workout.domain.local_repo.LocalWorkoutRepo
import com.hitenderpannu.workout.entity.Exercise
import com.hitenderpannu.workout.entity.ExerciseSet
import com.hitenderpannu.workout.entity.Workout
import com.hitenderpannu.workout.entity.WorkoutExercise
import com.hitenderpannu.workout.entity.WorkoutWithExercises
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.math.BigDecimal

class WorkoutInteractorImpl(
    private val localWorkoutRepo: LocalWorkoutRepo,
    private val exerciseRepo: LocalExerciseRepo
) : WorkoutInteractor {

    override suspend fun createNewWorkout(listOfExercises: List<Exercise>): Long {
        val newWorkoutId = localWorkoutRepo.createNewWorkout()
        listOfExercises.forEach {
            localWorkoutRepo.addExerciseToWorkout(newWorkoutId, it.id)
        }
        return newWorkoutId
    }

    override suspend fun createNewSet(workoutId: Long, exerciseId: String): WorkoutExercise {
        localWorkoutRepo.createNewSet(workoutId, exerciseId)
        return localWorkoutRepo.getExerciseForWorkout(workoutId, exerciseId)
    }

    override suspend fun addUpdateSet(set: ExerciseSet): WorkoutExercise {
        localWorkoutRepo.addOrUpdateSet(set)
        return localWorkoutRepo.getExerciseForWorkout(set.workoutId, set.exerciseId)
    }

    override suspend fun getWorkout(workoutId: Long): WorkoutWithExercises {
        val (workout, attachedExercisesIds) = localWorkoutRepo.getWorkoutWithAttachedExerciseList(workoutId)
        val exercise = attachedExercisesIds.map {
            val exercise = exerciseRepo.getExerciseWithBodyPartsAndEquipment(it)
            val sets = localWorkoutRepo.getSetsFor(workoutId, it)
            WorkoutExercise(workoutId, exercise, sets)
        }
        return WorkoutWithExercises(
            workoutId,
            workout.createdAt,
            workout.isFinished,
            exercise
        )
    }

    override suspend fun getPreviousWorkout(): Flow<WorkoutWithExercises?> {
        return localWorkoutRepo.getPreviousWorkout().map {
            if(it!=null) getWorkout(it.workoutId)
            else null
        }
    }

    override suspend fun updateRepCount(setId: Long, newRepCount: Int) {
        localWorkoutRepo.updateRepCount(setId, newRepCount)
    }

    override suspend fun updateWeight(setId: Long, newWeight: Double) {
        localWorkoutRepo.updateWeight(setId, newWeight)
    }

    override suspend fun finishWorkout(workoutId: Long) {
        localWorkoutRepo.finishWorkout(workoutId)
    }

    override suspend fun getTotalNumberOfWorkouts(): Flow<Int> {
        return localWorkoutRepo.getTotalNumberOfWorkouts()
    }

    override suspend fun getTotalAmountOfWeightLifted(): Flow<BigDecimal> {
        return localWorkoutRepo.getTotalAmountOfWeightLifted()
    }
}
