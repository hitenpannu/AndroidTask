package com.hitenderpannu.workout.data.local.repo

import com.hitenderpannu.workout.data.local.Mappers
import com.hitenderpannu.workout.data.local.dao.BodyPartsDao
import com.hitenderpannu.workout.data.local.dao.EquipmentsDao
import com.hitenderpannu.workout.data.local.dao.ExerciseDao
import com.hitenderpannu.workout.data.local.entities.BodyPartEntity
import com.hitenderpannu.workout.data.local.entities.EquipmentEntity
import com.hitenderpannu.workout.data.local.entities.ExerciseBodyCrossRef
import com.hitenderpannu.workout.data.local.entities.ExerciseEntity
import com.hitenderpannu.workout.data.local.entities.ExerciseEquipmentCrossRef
import com.hitenderpannu.workout.data.local.entities.ExerciseWithBodyPartsAndEquipments
import com.hitenderpannu.workout.domain.local_repo.LocalExerciseRepo
import com.hitenderpannu.workout.entity.Exercise

class LocalExerciseRepoImpl(
    private val exerciseDao: ExerciseDao,
    private val bodyPartsDao: BodyPartsDao,
    private val equipmentsDao: EquipmentsDao
) : LocalExerciseRepo {

    override suspend fun getAllExercises(): List<Exercise> {
        return exerciseDao.getAll().map { Mappers.mapToExercise(it) }
    }

    override suspend fun insertExercises(list: List<Exercise>) {
        list.forEach { exercise ->
            val bodyParts = exercise.bodyParts.map { BodyPartEntity(it.id, it.name) }
            bodyPartsDao.insertAll(bodyParts)
            val equipments = exercise.equipments.map { EquipmentEntity(it.id, it.name) }
            equipmentsDao.insertAll(equipments)

            exerciseDao.insertExercise(ExerciseEntity(exercise.id, exercise.name))

            bodyParts.forEach {
                exerciseDao.insertAllBodyPartsReferences(ExerciseBodyCrossRef(exerciseId = exercise.id, bodyPartId = it.id))
            }

            equipments.forEach {
                exerciseDao.insertAllEquipmentReferences(ExerciseEquipmentCrossRef(exerciseId = exercise.id, equipmentId = it.id))
            }
        }
    }

    private fun map(exercise: Exercise): ExerciseWithBodyPartsAndEquipments {
        return ExerciseWithBodyPartsAndEquipments(
            ExerciseEntity(exercise.id, exercise.name),
            exercise.bodyParts.map { BodyPartEntity(it.id, it.name) },
            exercise.equipments.map { EquipmentEntity(it.id, it.name) }
        )
    }

    override suspend fun getExerciseWithBodyPartsAndEquipment(id: String): Exercise {
        return Mappers.mapToExercise(exerciseDao.getExerciseWhere(id))
    }
}
