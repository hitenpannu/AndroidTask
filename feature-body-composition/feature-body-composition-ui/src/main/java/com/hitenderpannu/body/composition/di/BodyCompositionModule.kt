package com.hitenderpannu.body.composition.di

import android.content.Context
import com.hitenderpannu.body.composition.data.local.DatabaseManager
import com.hitenderpannu.body.composition.data.local.dao.PrimaryBodyCompositionDao
import com.hitenderpannu.body.composition.data.local.repo.LocalBodyCompositionRepoImpl
import com.hitenderpannu.body.composition.domain.BodyCompositionInteractor
import com.hitenderpannu.body.composition.domain.BodyCompositionInteractorImpl
import com.hitenderpannu.body.composition.domain.local_repo.LocalBodyCompositionRepo
import dagger.Module
import dagger.Provides

@Module
class BodyCompositionModule(private val applicationContext: Context) {

    @BodyCompositionScope
    @Provides
    fun provideBodyCompositionDao(): PrimaryBodyCompositionDao {
        val database = DatabaseManager.getInstance(applicationContext)
        return database.getPrimaryCompositionDao()
    }

    @BodyCompositionScope
    @Provides
    fun provideBodyCompositionLocalRepo(
        primaryBodyCompositionDao: PrimaryBodyCompositionDao
    ): LocalBodyCompositionRepo {
        return LocalBodyCompositionRepoImpl(primaryBodyCompositionDao)
    }

    @BodyCompositionScope
    @Provides
    fun provideBodyCompositionInteractor(
        localBodyCompositionRepo: LocalBodyCompositionRepo
    ): BodyCompositionInteractor {
        return BodyCompositionInteractorImpl(localBodyCompositionRepo)
    }
}
