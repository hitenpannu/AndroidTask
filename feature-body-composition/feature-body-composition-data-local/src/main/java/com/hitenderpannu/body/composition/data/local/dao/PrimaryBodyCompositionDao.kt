package com.hitenderpannu.body.composition.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.hitenderpannu.body.composition.data.local.entities.PrimaryBodyCompositionEntity
import com.hitenderpannu.body.composition.data.local.entities.PrimaryBodyCompositionEntity.Companion.TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface PrimaryBodyCompositionDao {

    @Transaction
    @Query("SELECT * FROM $TABLE_NAME ORDER BY createdOn DESC")
    fun getAll(): Flow<List<PrimaryBodyCompositionEntity>>

    @Insert
    fun addNew(primaryBodyCompositionEntity: PrimaryBodyCompositionEntity)

    @Update
    fun update(primaryBodyCompositionEntity: PrimaryBodyCompositionEntity)

    @Delete
    fun delete(primaryBodyCompositionEntity: PrimaryBodyCompositionEntity)

    @Transaction
    @Query("SELECT * FROM $TABLE_NAME ORDER BY createdOn DESC LIMIT 1")
    fun getLatest(): Flow<PrimaryBodyCompositionEntity>
}
