package com.hitenderpannu.workout.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

internal const val TABLE_EQUIPMENT = "equipment"

@Entity(tableName = TABLE_EQUIPMENT)
data class EquipmentEntity(
    @PrimaryKey
    @ColumnInfo(name = "equipmentId")val id: String, val name: String)
