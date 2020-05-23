package com.hitenderpannu.workout.data.local

import com.hitenderpannu.common.entity.WeightUnit
import com.hitenderpannu.workout.data.local.entities.ExerciseWithBodyPartsAndEquipments
import com.hitenderpannu.workout.data.local.entities.SetEntity
import com.hitenderpannu.workout.entity.BodyPart
import com.hitenderpannu.workout.entity.Equipment
import com.hitenderpannu.workout.entity.Exercise
import com.hitenderpannu.workout.entity.ExerciseSet
import java.math.BigDecimal

object Mappers {

    fun mapToExercise(exerciseWithBodyPartsAndEquipments: ExerciseWithBodyPartsAndEquipments): Exercise {
        return Exercise(
            exerciseWithBodyPartsAndEquipments.exercise.id,
            exerciseWithBodyPartsAndEquipments.exercise.name,
            exerciseWithBodyPartsAndEquipments.bodyParts.map { BodyPart(it.id, it.name) },
            exerciseWithBodyPartsAndEquipments.equipments.map { Equipment(it.id, it.name) }
        )
    }

    fun mapToSetEntity(exerciseSet: ExerciseSet): SetEntity {
        return SetEntity(
            id = exerciseSet.setId,
            workoutId = exerciseSet.workoutId,
            exerciseId = exerciseSet.exerciseId,
            createdAt = exerciseSet.createdAt,
            numberOfReps = exerciseSet.repCount,
            weightUnit = exerciseSet.weightUnit.intValue,
            weight = exerciseSet.weight.toDouble(),
            duration = exerciseSet.duration
        )
    }

    fun mapToExerciseSet(setEntity: SetEntity): ExerciseSet {
        return ExerciseSet(
            setId = setEntity.id,
            workoutId = setEntity.workoutId,
            exerciseId = setEntity.exerciseId,
            repCount = setEntity.numberOfReps,
            weight = BigDecimal(setEntity.weight),
            weightUnit = WeightUnit.values().first { it.intValue == setEntity.weightUnit },
            duration = setEntity.duration,
            createdAt = setEntity.createdAt
        )
    }
}
