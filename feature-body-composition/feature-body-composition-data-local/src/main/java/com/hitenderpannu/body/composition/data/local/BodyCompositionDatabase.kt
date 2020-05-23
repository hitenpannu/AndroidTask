package com.hitenderpannu.body.composition.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hitenderpannu.body.composition.data.local.dao.PrimaryBodyCompositionDao
import com.hitenderpannu.body.composition.data.local.entities.PrimaryBodyCompositionEntity

@Database(
    entities = [PrimaryBodyCompositionEntity::class],
    version = 1
)
abstract class BodyCompositionDatabase : RoomDatabase() {

    abstract fun getPrimaryCompositionDao(): PrimaryBodyCompositionDao
}
