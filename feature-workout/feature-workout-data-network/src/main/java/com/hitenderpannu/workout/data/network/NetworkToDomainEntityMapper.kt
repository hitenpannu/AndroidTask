package com.hitenderpannu.workout.data.network

import com.hitenderpannu.workout.data.network.network_entity.NetworkBodyPart
import com.hitenderpannu.workout.data.network.network_entity.NetworkEquipment
import com.hitenderpannu.workout.data.network.network_entity.NetworkExercise
import com.hitenderpannu.workout.entity.BodyPart
import com.hitenderpannu.workout.entity.Equipment
import com.hitenderpannu.workout.entity.Exercise

object NetworkToDomainEntityMapper {

    fun map(networkBodyPart: NetworkBodyPart): BodyPart {
        return BodyPart(networkBodyPart.id, networkBodyPart.name)
    }

    fun map(networkEquipment: NetworkEquipment): Equipment {
        return Equipment(networkEquipment.id, networkEquipment.name)
    }

    fun map(networkExercise: NetworkExercise): Exercise {
        return Exercise(
            networkExercise.id,
            networkExercise.name,
            networkExercise.bodyParts.map { map(it) },
            networkExercise.equipments.map { map(it) }
        )
    }
}
