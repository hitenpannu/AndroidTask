package com.hitenderpannu.body.composition.domain.local_repo

import com.hitenderpannu.body.composition.entity.PrimaryBodyComposition
import kotlinx.coroutines.flow.Flow

interface LocalBodyCompositionRepo {

    fun getAll(): Flow<List<PrimaryBodyComposition>>

    fun addNew(composition: PrimaryBodyComposition)

    fun update(composition: PrimaryBodyComposition)

    fun getLatest(): PrimaryBodyComposition
}
