package com.hitenderpannu.workout.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

internal const val TABLE_EXERCISE = "exercise"

@Entity(tableName = TABLE_EXERCISE)
data class ExerciseEntity(
    @PrimaryKey
    @ColumnInfo(name = "exerciseId") val id: String,
    val name: String
)

@Entity(
    tableName = "ExerciseBodyCrossRef",
    primaryKeys = ["exerciseId", "bodyPartId"])
data class ExerciseBodyCrossRef(
    val exerciseId: String,
    val bodyPartId: String
)

@Entity(
    tableName = "ExerciseEquipmentCrossRef",
    primaryKeys = ["exerciseId", "equipmentId"])
data class ExerciseEquipmentCrossRef(
    val exerciseId: String,
    val equipmentId: String
)

data class ExerciseWithBodyPartsAndEquipments(
    @Embedded val exercise: ExerciseEntity,
    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "bodyPartId",
        associateBy = Junction(ExerciseBodyCrossRef::class),
        entity = BodyPartEntity::class
    )
    val bodyParts: List<BodyPartEntity>,
    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "equipmentId",
        associateBy = Junction(ExerciseEquipmentCrossRef::class),
        entity = EquipmentEntity::class
    )
    val equipments: List<EquipmentEntity>
)
