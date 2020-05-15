package com.hitenderpannu.workout.di.workout

import com.hitenderpannu.base.di.CoreComponent
import com.hitenderpannu.common.utils.NetworkConnectionChecker
import com.hitenderpannu.workout.di.WorkoutScope
import com.hitenderpannu.workout.domain.local_repo.LocalExerciseRepo
import dagger.Component
import retrofit2.Retrofit

@WorkoutScope
@Component(
    modules = [WorkoutModule::class],
    dependencies = [CoreComponent::class]
)
interface WorkoutComponent {

    fun provideLocalExerciseRepo(): LocalExerciseRepo

    fun provideNetworkConnectionChecker(): NetworkConnectionChecker

    fun provideRetrofit(): Retrofit
}
