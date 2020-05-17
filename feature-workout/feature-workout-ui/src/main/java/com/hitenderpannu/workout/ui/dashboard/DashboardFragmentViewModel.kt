package com.hitenderpannu.workout.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitenderpannu.workout.domain.WorkoutInteractor
import com.hitenderpannu.workout.entity.Workout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardFragmentViewModel(
    private val workoutInteractor: WorkoutInteractor
) : ViewModel() {

    private val mutableProgress = MutableLiveData<Boolean>()
    val progressLiveData: LiveData<Boolean> = mutableProgress

    private val mutableUnFinishedWorkout = MutableLiveData<Workout?>()
    val unFinishedWorkout: LiveData<Workout?> = mutableUnFinishedWorkout

    init {
        getUnfinishedWorkout()
    }

    private fun getUnfinishedWorkout() {
        CoroutineScope(Dispatchers.IO).launch {
            val workoutExercise = workoutInteractor.getUnFinishedWorkout()
            mutableUnFinishedWorkout.postValue(workoutExercise)
        }
    }
}
