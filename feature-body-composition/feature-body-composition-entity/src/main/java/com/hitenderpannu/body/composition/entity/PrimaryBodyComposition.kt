package com.hitenderpannu.body.composition.entity

import com.hitenderpannu.common.entity.WeightUnit
import java.math.BigDecimal

data class PrimaryBodyComposition(
    val id: Long = 0,
    val totalWeight: BigDecimal,
    val fatPercentage: BigDecimal,
    val muscleWeight: BigDecimal,
    val weightUnit: WeightUnit,
    val createdOn: Long
)
