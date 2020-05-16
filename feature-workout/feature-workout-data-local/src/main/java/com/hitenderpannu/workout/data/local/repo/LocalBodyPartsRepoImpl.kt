package com.hitenderpannu.workout.data.local.repo

import com.hitenderpannu.workout.data.local.dao.BodyPartsDao
import com.hitenderpannu.workout.entity.BodyPart

class LocalBodyPartsRepoImpl(private val bodyPartsDao: BodyPartsDao) :
    com.hitenderpannu.workout.domain.local_repo.LocalBodyPartsRepo {

    override suspend fun getAllBodyParts(): List<BodyPart> {
        return bodyPartsDao.getAll().map { BodyPart(it.id, it.name) }
    }
}
