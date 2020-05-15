package com.hitenderpannu.workout.domain.local_repo

import com.hitenderpannu.workout.entity.Equipment

interface LocalEquipmentsRepo {
    suspend fun getAllEquipments(): List<Equipment>
}
