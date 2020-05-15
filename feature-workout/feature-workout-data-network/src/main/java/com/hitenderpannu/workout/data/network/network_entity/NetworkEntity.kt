package com.hitenderpannu.workout.data.network.network_entity

import com.google.gson.annotations.SerializedName

data class NetworkExercise(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("bodyParts")
    val bodyParts: List<NetworkBodyPart>,
    @SerializedName("equipments")
    val equipments: List<NetworkEquipment>
)

data class NetworkBodyPart(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String
)

data class NetworkEquipment(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String
)


