package com.hitenderpannu.workout.di.add_exercise

import com.hitenderpannu.base.di.CoreComponent
import com.hitenderpannu.workout.di.AddExerciseScope
import com.hitenderpannu.workout.ui.addExercise.AddExerciseFragment
import dagger.Component

@AddExerciseScope
@Component(
    modules = [AddExerciseModule::class],
    dependencies = [CoreComponent::class]
)
interface AddExerciseComponent {

    fun inject(addExerciseFragment: AddExerciseFragment)
}
