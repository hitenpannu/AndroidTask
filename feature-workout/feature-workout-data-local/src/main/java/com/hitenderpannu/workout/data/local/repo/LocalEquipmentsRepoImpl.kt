package com.hitenderpannu.workout.data.local.repo

import com.hitenderpannu.workout.data.local.dao.EquipmentsDao
import com.hitenderpannu.workout.entity.Equipment

class LocalEquipmentsRepoImpl(private val equipmentsDao: EquipmentsDao) :
    com.hitenderpannu.workout.domain.local_repo.LocalEquipmentsRepo {

    override suspend fun getAllEquipments(): List<Equipment> {
        return equipmentsDao.getAll().value?.map { Equipment(it.id, it.name) } ?: listOf()
    }
}
