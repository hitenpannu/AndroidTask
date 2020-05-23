package com.hitenderpannu.workout.data.local.repo

import androidx.sqlite.db.SimpleSQLiteQuery
import com.hitenderpannu.common.entity.WeightUnit
import com.hitenderpannu.workout.data.local.Mappers
import com.hitenderpannu.workout.data.local.dao.ExerciseDao
import com.hitenderpannu.workout.data.local.dao.WorkoutDao
import com.hitenderpannu.workout.data.local.entities.SetEntity
import com.hitenderpannu.workout.data.local.entities.WorkoutEntity
import com.hitenderpannu.workout.data.local.entities.WorkoutExerciseCrossRef
import com.hitenderpannu.workout.domain.local_repo.LocalWorkoutRepo
import com.hitenderpannu.workout.entity.ExerciseSet
import com.hitenderpannu.workout.entity.Workout
import com.hitenderpannu.workout.entity.WorkoutExercise
import com.hitenderpannu.workout.entity.WorkoutWithExercises
import java.math.BigDecimal

class LocalWorkoutRepoImpl(
    private val workoutDao: WorkoutDao,
    private val exerciseDao: ExerciseDao
) : LocalWorkoutRepo {

    override suspend fun createNewWorkout(): Long {
        val workout = WorkoutEntity(createdAt = System.currentTimeMillis())
        return workoutDao.insertNewWorkout(workout)
    }

    override suspend fun addExerciseToWorkout(workoutId: Long, exerciseId: String) {
        val ref = WorkoutExerciseCrossRef(workoutId, exerciseId)
        workoutDao.insertWorkoutExerciseCross(ref)
    }

    override suspend fun getWorkoutWithAttachedExerciseList(workoutId: Long): Pair<WorkoutWithExercises, List<String>> {
        val workoutWithExercise = workoutDao.getWorkoutWithExerciseWhere(workoutId)
        val exerciseIds = workoutWithExercise.exerciseList.map { it.id }
        val baseWorkout = workoutWithExercise.workout.run { WorkoutWithExercises(id, createdAt, isFinished, listOf()) }
        return Pair(baseWorkout, exerciseIds)
    }

    override suspend fun getExerciseForWorkout(workoutId: Long, exerciseId: String): WorkoutExercise {
        val exercise = exerciseDao.getExerciseWhere(exerciseId)
        val sets = workoutDao.getListOfSetsWhere(workoutId, exerciseId)
        return WorkoutExercise(
            workoutId,
            Mappers.mapToExercise(exercise),
            sets.map { Mappers.mapToExerciseSet(it) }
        )
    }

    override suspend fun getSetsFor(workoutId: Long, exerciseId: String): List<ExerciseSet> {
        return workoutDao.getListOfSetsWhere(workoutId, exerciseId)
            .map { set -> Mappers.mapToExerciseSet(set) }
    }

    override suspend fun addOrUpdateSet(exerciseSet: ExerciseSet) {
        val set = Mappers.mapToSetEntity(exerciseSet)
        workoutDao.updateSet(set)
    }

    override suspend fun createNewSet(workoutId: Long, exerciseId: String) {
        val entity = SetEntity(
            workoutId = workoutId,
            exerciseId = exerciseId,
            createdAt = System.currentTimeMillis(),
            numberOfReps = 0,
            weight = BigDecimal(0.0).toDouble(),
            weightUnit = WeightUnit.KG.intValue,
            duration = 0L
        )
        workoutDao.updateSet(entity)
    }

    override suspend fun getUnfinishedWorkout(): Workout? {
        val workoutEntity = workoutDao.getUnfinishedWorkout() ?: return null
        return Workout(workoutEntity.id, workoutEntity.createdAt, workoutEntity.isFinished)
    }

    override suspend fun updateRepCount(setId: Long, newRepCount: Int) {
        val updateQuery = SimpleSQLiteQuery("UPDATE ${SetEntity.TABLE_NAME} SET numberOfReps=$newRepCount WHERE id=$setId")
        workoutDao.updateData(updateQuery)
    }

    override suspend fun updateWeight(setId: Long, newWeight: Double) {
        val updateQuery = SimpleSQLiteQuery("UPDATE ${SetEntity.TABLE_NAME} SET weight=$newWeight WHERE id=$setId")
        workoutDao.updateData(updateQuery)
    }

    override suspend fun finishWorkout(workoutId: Long) {
        val updateQuery = SimpleSQLiteQuery("UPDATE ${WorkoutEntity.TABLE_NAME} SET isFinished=1 WHERE id=$workoutId")
        workoutDao.updateData(updateQuery)
    }

    override suspend fun getPreviousWorkout(): Workout? {
        return workoutDao.getPreviouslyClosedWorkout()?.run { Workout(id, this.createdAt, this.isFinished) }
    }
}
