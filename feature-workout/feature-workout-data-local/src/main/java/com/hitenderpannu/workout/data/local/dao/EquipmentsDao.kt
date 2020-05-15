package com.hitenderpannu.workout.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hitenderpannu.workout.data.local.entities.BodyPartEntity
import com.hitenderpannu.workout.data.local.entities.EquipmentEntity
import com.hitenderpannu.workout.data.local.entities.TABLE_BODY_PART
import com.hitenderpannu.workout.data.local.entities.TABLE_EQUIPMENT

@Dao
interface EquipmentsDao {

    @Query("SELECT * FROM $TABLE_EQUIPMENT")
    fun getAll(): LiveData<List<EquipmentEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(equipments: List<EquipmentEntity>)
}
