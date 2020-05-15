package com.hitenderpannu.workout.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hitenderpannu.workout.data.local.entities.BodyPartEntity
import com.hitenderpannu.workout.data.local.entities.TABLE_BODY_PART

@Dao
interface BodyPartsDao {

    @Query("SELECT * FROM $TABLE_BODY_PART")
    fun getAll(): LiveData<List<BodyPartEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(bodyParts: List<BodyPartEntity>)
}
