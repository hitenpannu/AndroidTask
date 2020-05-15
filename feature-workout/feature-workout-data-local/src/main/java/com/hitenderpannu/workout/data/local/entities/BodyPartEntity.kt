package com.hitenderpannu.workout.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

internal const val TABLE_BODY_PART = "bodyPart"

@Entity(tableName = TABLE_BODY_PART)
data class BodyPartEntity(
    @PrimaryKey
    @ColumnInfo(name = "bodyPartId")val id: String, val name: String)
