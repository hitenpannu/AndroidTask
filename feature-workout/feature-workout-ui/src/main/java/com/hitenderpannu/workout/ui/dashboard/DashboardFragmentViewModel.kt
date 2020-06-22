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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigDecimal

class DashboardFragmentViewModel(
    private val workoutInteractor: WorkoutInteractor
) : ViewModel() {

    private val mutableProgress = MutableLiveData<Boolean>()
    val progressLiveData: LiveData<Boolean> = mutableProgress

    private val mutableNoWorkout = MutableLiveData<Boolean>()
    val noWorkout: LiveData<Boolean> = mutableNoWorkout

    private val mutableUnFinishedWorkout = MutableLiveData<Workout?>()
    val unFinishedWorkout: LiveData<Workout?> = mutableUnFinishedWorkout

    private val mutablePreviousWorkout = MutableLiveData<WorkoutWithExercises?>()
    val previousWorkout: LiveData<WorkoutWithExercises?> = mutablePreviousWorkout

    private val mutableNumberOfWorkouts = MutableLiveData<Int>()
    val numberOfWorkouts: LiveData<Int> = mutableNumberOfWorkouts

    private val mutableTotalAmountOfWeightLifted = MutableLiveData<BigDecimal>()
    val totalAmountOfWeightLifted: LiveData<BigDecimal> = mutableTotalAmountOfWeightLifted

    fun fetchUpdatedData() {
        CoroutineScope(Dispatchers.IO).launch {
            mutableProgress.postValue(false)
            try {
                getWorkoutDetails()
                getHeaderInfo()
            } catch (error: Throwable) {
                Log.e("ERROR", error.message)
            } finally {
                mutableProgress.postValue(true)
            }
        }
    }

    private suspend fun getWorkoutDetails() {
        workoutInteractor.getPreviousWorkout().collect { workoutEntity ->
            mutableNoWorkout.postValue(workoutEntity == null)
            if (workoutEntity == null) return@collect
            mutablePreviousWorkout.postValue(null)
            mutableUnFinishedWorkout.postValue(null)
            if (workoutEntity.isFinished) {
                mutablePreviousWorkout.postValue(workoutEntity)
            } else {
                mutableUnFinishedWorkout.postValue(Workout(workoutEntity.workoutId, workoutEntity.createdAt, workoutEntity.isFinished))
            }
        }
    }

    private suspend fun getHeaderInfo() {
        workoutInteractor.getTotalNumberOfWorkouts().collect {
            mutableNumberOfWorkouts.postValue(it)
        }

        workoutInteractor.getTotalAmountOfWeightLifted().collect {
            mutableTotalAmountOfWeightLifted.postValue(it)
        }
    }
}
