package com.hitenderpannu.workout.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitenderpannu.workout.domain.WorkoutInteractor
import com.hitenderpannu.workout.entity.Workout
import com.hitenderpannu.workout.entity.WorkoutWithExercises
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

    private val mutablePreviousWorkout = MutableLiveData<WorkoutWithExercises>()
    val previousWorkout: LiveData<WorkoutWithExercises?> = mutablePreviousWorkout

    init {
        fetchUpdatedData()
    }

    fun fetchUpdatedData() {
        CoroutineScope(Dispatchers.IO).launch {
            mutableProgress.postValue(false)
            try {
                val previousWorkout = workoutInteractor.getPreviousWorkout()
                mutablePreviousWorkout.postValue(previousWorkout)
                val workoutExercise = workoutInteractor.getUnFinishedWorkout()
                mutableUnFinishedWorkout.postValue(workoutExercise)
            } catch (error: Throwable) {
                Log.e("ERROR", error.message)
            } finally {
                mutableProgress.postValue(true)
            }
        }
    }
}
