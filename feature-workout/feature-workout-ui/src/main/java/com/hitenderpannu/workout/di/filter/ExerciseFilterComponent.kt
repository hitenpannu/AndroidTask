package com.hitenderpannu.workout.di.filter

import com.hitenderpannu.workout.di.ExerciseFilterScope
import com.hitenderpannu.workout.di.workout.WorkoutComponent
import com.hitenderpannu.workout.ui.exercise_filters.ExerciseFiltersFragment
import dagger.Component

@ExerciseFilterScope
@Component(
    modules = [ExerciseFilterFragmentViewModule::class],
    dependencies = [WorkoutComponent::class]
)
interface ExerciseFilterComponent {

    fun inject(exerciseFilter: ExerciseFiltersFragment)
}
