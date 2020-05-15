package com.hitenderpannu.workout.di

import com.hitenderpannu.base.MainApplication
import com.hitenderpannu.workout.di.add_exercise.AddExerciseModule
import com.hitenderpannu.workout.di.add_exercise.DaggerAddExerciseComponent
import com.hitenderpannu.workout.di.dashboard.DaggerDashboardComponent
import com.hitenderpannu.workout.ui.addExercise.AddExerciseFragment
import com.hitenderpannu.workout.ui.dashboard.DashBoardFragment

object DaggerManager {

    fun inject(dashboard: DashBoardFragment) {
        val coreComponent = (dashboard.requireActivity().application as MainApplication).coreComponent
        DaggerDashboardComponent.builder().coreComponent(coreComponent).build().inject(dashboard)
    }

    fun inject(addExerciseFragment: AddExerciseFragment) {
        val coreComponent = (addExerciseFragment.requireActivity().application as MainApplication?)?.coreComponent
        DaggerAddExerciseComponent.builder()
            .coreComponent(coreComponent)
            .addExerciseModule(AddExerciseModule(addExerciseFragment))
            .build().inject(addExerciseFragment)
    }
}
