package com.hitenderpannu.body.composition.domain

import com.hitenderpannu.body.composition.entity.PrimaryBodyComposition
import kotlinx.coroutines.flow.Flow

interface BodyCompositionInteractor {

    fun getAllEntries(): Flow<List<PrimaryBodyComposition>>

    fun addNewEntry(primaryBodyComposition: PrimaryBodyComposition)

    fun updateEntry(primaryBodyComposition: PrimaryBodyComposition)

    fun getLatestEntry(): Flow<PrimaryBodyComposition>
}
