package com.hitenderpannu.workout.di.add_exercise

import com.hitenderpannu.workout.di.AddExerciseScope
import com.hitenderpannu.workout.di.workout.WorkoutComponent
import com.hitenderpannu.workout.ui.addExercise.AddExerciseFragment
import dagger.Component

@AddExerciseScope
@Component(
    modules = [AddExerciseModule::class],
    dependencies = [WorkoutComponent::class]
)
interface AddExerciseComponent {

    fun inject(addExerciseFragment: AddExerciseFragment)
}
