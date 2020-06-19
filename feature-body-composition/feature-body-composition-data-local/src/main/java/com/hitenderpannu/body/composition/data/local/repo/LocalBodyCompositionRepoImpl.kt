package com.hitenderpannu.body.composition.data.local.repo

import com.hitenderpannu.body.composition.data.local.PrimaryCompositionMapper
import com.hitenderpannu.body.composition.data.local.dao.PrimaryBodyCompositionDao
import com.hitenderpannu.body.composition.domain.local_repo.LocalBodyCompositionRepo
import com.hitenderpannu.body.composition.entity.PrimaryBodyComposition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalBodyCompositionRepoImpl(
    private val bodyCompositionDao: PrimaryBodyCompositionDao
) : LocalBodyCompositionRepo {
    override fun getAll(): Flow<List<PrimaryBodyComposition>> {
        return bodyCompositionDao.getAll().map { it.map { PrimaryCompositionMapper.to(it) } }
    }

    override fun addNew(composition: PrimaryBodyComposition) {
        val entity = PrimaryCompositionMapper.from(composition)
        bodyCompositionDao.addNew(entity)
    }

    override fun update(composition: PrimaryBodyComposition) {
        val entity = PrimaryCompositionMapper.from(composition)
        bodyCompositionDao.update(entity)
    }

    override fun getLatest(): Flow<PrimaryBodyComposition> {
        return bodyCompositionDao.getLatest().map { PrimaryCompositionMapper.to(it) }
    }
}
