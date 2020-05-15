package com.hitenderpannu.workout.domain.local_repo

import com.hitenderpannu.workout.entity.BodyPart

interface LocalBodyPartsRepo {
    suspend fun getAllBodyParts(): List<BodyPart>
}
