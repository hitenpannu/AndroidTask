package com.hitenderpannu.workout.entity

data class Exercise(
    val id: String,
    val name:String,
    val bodyParts: List<BodyPart>,
    val equipments: List<Equipment>)
