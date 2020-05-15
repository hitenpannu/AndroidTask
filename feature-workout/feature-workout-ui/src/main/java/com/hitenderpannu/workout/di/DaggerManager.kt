package com.hitenderpannu.workout.di

import com.hitenderpannu.base.MainApplication
import com.hitenderpannu.workout.di.add_exercise.AddExerciseModule
import com.hitenderpannu.workout.di.add_exercise.DaggerAddExerciseComponent
import com.hitenderpannu.workout.di.dashboard.DaggerDashboardComponent
import com.hitenderpannu.workout.di.workout.DaggerWorkoutComponent
import com.hitenderpannu.workout.di.workout.WorkoutComponent
import com.hitenderpannu.workout.di.workout.WorkoutModule
import com.hitenderpannu.workout.ui.addExercise.AddExerciseFragment
import com.hitenderpannu.workout.ui.dashboard.DashBoardFragment

object DaggerManager {

    private var workoutComponent: WorkoutComponent? = null

    fun inject(dashboard: DashBoardFragment) {
        if (workoutComponent == null) initializeWorkoutComponent(dashboard.requireActivity().application as MainApplication)
        DaggerDashboardComponent.builder().workoutComponent(workoutComponent).build().inject(dashboard)
    }

    private fun initializeWorkoutComponent(mainApplication: MainApplication) {
        workoutComponent = DaggerWorkoutComponent.builder()
            .coreComponent(mainApplication.coreComponent)
            .workoutModule(WorkoutModule(mainApplication))
            .build()
    }

    fun inject(addExerciseFragment: AddExerciseFragment) {
        if (workoutComponent == null) initializeWorkoutComponent(addExerciseFragment.requireActivity().application as MainApplication)
        DaggerAddExerciseComponent.builder()
            .workoutComponent(workoutComponent)
            .addExerciseModule(AddExerciseModule(addExerciseFragment))
            .build().inject(addExerciseFragment)
    }
}