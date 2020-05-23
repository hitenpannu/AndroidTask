package com.hitenderpannu.body.composition.data.local

import com.hitenderpannu.body.composition.data.local.entities.PrimaryBodyCompositionEntity
import com.hitenderpannu.body.composition.entity.PrimaryBodyComposition
import com.hitenderpannu.common.domain.Mapper
import com.hitenderpannu.common.entity.WeightUnit
import java.math.BigDecimal

object PrimaryCompositionMapper : Mapper<PrimaryBodyCompositionEntity, PrimaryBodyComposition> {
    override fun to(t: PrimaryBodyCompositionEntity): PrimaryBodyComposition {
        return PrimaryBodyComposition(
            t.id,
            BigDecimal(t.totalWeight),
            BigDecimal(t.fatPercentage),
            BigDecimal(t.muscleWeight),
            WeightUnit.values().first { it.intValue == t.weightUnit },
            t.createdOn
        )
    }

    override fun from(r: PrimaryBodyComposition): PrimaryBodyCompositionEntity {
        return PrimaryBodyCompositionEntity(
            r.id,
            r.totalWeight.toDouble(),
            r.fatPercentage.toDouble(),
            r.muscleWeight.toDouble(),
            r.weightUnit.intValue,
            r.createdOn
        )
    }
}
