package com.hitenderpannu.body.composition.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = PrimaryBodyCompositionEntity.TABLE_NAME)
data class PrimaryBodyCompositionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val totalWeight: Double,
    val fatPercentage: Double,
    val muscleWeight: Double,
    val weightUnit: Int,
    val createdOn: Long
){
    companion object{
        const val TABLE_NAME = "primaryComposition"
    }
}
