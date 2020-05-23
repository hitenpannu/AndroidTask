package com.hitenderpannu.body.composition.domain

import com.hitenderpannu.body.composition.domain.local_repo.LocalBodyCompositionRepo
import com.hitenderpannu.body.composition.entity.PrimaryBodyComposition
import kotlinx.coroutines.flow.Flow

class BodyCompositionInteractorImpl(private val localRepo: LocalBodyCompositionRepo) : BodyCompositionInteractor {
    override fun getAllEntries(): Flow<List<PrimaryBodyComposition>> {
        return localRepo.getAll()
    }

    override fun addNewEntry(primaryBodyComposition: PrimaryBodyComposition) {
        return localRepo.addNew(primaryBodyComposition)
    }

    override fun updateEntry(primaryBodyComposition: PrimaryBodyComposition) {
        return localRepo.update(primaryBodyComposition)
    }

    override fun getLatestEntry(): PrimaryBodyComposition {
        return localRepo.getLatest()
    }
}
