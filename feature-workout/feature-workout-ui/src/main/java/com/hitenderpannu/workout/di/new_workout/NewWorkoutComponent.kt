package com.hitenderpannu.workout.di.new_workout

import com.hitenderpannu.workout.di.NewWorkoutScope
import com.hitenderpannu.workout.di.workout.WorkoutComponent
import com.hitenderpannu.workout.ui.new_workout.NewWorkoutFragment
import dagger.Component

@NewWorkoutScope
@Component(
    modules = [NewWorkoutModule::class],
    dependencies = [WorkoutComponent::class]
)
interface NewWorkoutComponent {

    fun inject(newWorkoutFragment: NewWorkoutFragment)
}
