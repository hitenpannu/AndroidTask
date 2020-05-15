package com.hitenderpannu.workout.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hitenderpannu.workout.data.local.dao.BodyPartsDao
import com.hitenderpannu.workout.data.local.dao.EquipmentsDao
import com.hitenderpannu.workout.data.local.dao.ExerciseDao
import com.hitenderpannu.workout.data.local.entities.BodyPartEntity
import com.hitenderpannu.workout.data.local.entities.EquipmentEntity
import com.hitenderpannu.workout.data.local.entities.ExerciseBodyCrossRef
import com.hitenderpannu.workout.data.local.entities.ExerciseEntity
import com.hitenderpannu.workout.data.local.entities.ExerciseEquipmentCrossRef

@Database(
    version = 1,
    entities = [
        ExerciseEntity::class,
        BodyPartEntity::class,
        EquipmentEntity::class,
        ExerciseBodyCrossRef::class,
        ExerciseEquipmentCrossRef::class]
)
abstract class WorkoutDatabase : RoomDatabase() {
    abstract fun bodyPartsDao(): BodyPartsDao
    abstract fun equipmentsDao(): EquipmentsDao
    abstract fun exerciseDao(): ExerciseDao
}
