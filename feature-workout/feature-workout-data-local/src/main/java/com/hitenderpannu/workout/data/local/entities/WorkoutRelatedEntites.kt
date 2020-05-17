package com.hitenderpannu.workout.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.hitenderpannu.workout.data.local.entities.WorkoutEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val createdAt: Long,
    val isFinished: Boolean = false
) {
    companion object {
        const val TABLE_NAME = "workout"
    }
}

@Entity(
    tableName = SetEntity.TABLE_NAME,
    indices = [Index(value = arrayOf("id", "workoutId", "exerciseId"))]
)
data class SetEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val workoutId: Long,
    val exerciseId: String,
    val createdAt: Long,
    val numberOfReps: Int,
    val weight: Double,
    val weightUnit: Int,
    val duration: Long
) {
    companion object {
        const val TABLE_NAME = "exerciseSet"
    }
}

@Entity(primaryKeys = ["id", "exerciseId"])
data class WorkoutExerciseCrossRef(
    @ColumnInfo(name = "id")val workoutId: Long,
    val exerciseId: String
)

data class WorkoutWithExercise(
    @Embedded val workout: WorkoutEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "exerciseId",
        associateBy = Junction(WorkoutExerciseCrossRef::class),
        entity = ExerciseEntity::class
    )
    val exerciseList: List<ExerciseEntity>
)
