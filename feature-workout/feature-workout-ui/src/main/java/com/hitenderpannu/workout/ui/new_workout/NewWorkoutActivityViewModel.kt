package com.hitenderpannu.workout.ui.new_workout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitenderpannu.workout.domain.WorkoutInteractor
import com.hitenderpannu.workout.entity.WorkoutExercise
import com.hitenderpannu.workout.entity.WorkoutWithExercises
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewWorkoutActivityViewModel(private val workoutInteractor: WorkoutInteractor) : ViewModel(), NewWorkoutAdapter.ExerciseCallback {

    private val mutableLoadingProgress = MutableLiveData<Boolean>()
    val loadingProgress: LiveData<Boolean> = mutableLoadingProgress

    private val mutableWorkoutWithExercises = MutableLiveData<WorkoutWithExercises>()
    val workoutWithExercises: LiveData<WorkoutWithExercises> = mutableWorkoutWithExercises

    private val mutableErrorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = mutableErrorMessage

    private val mutableExercise = MutableLiveData<WorkoutExercise>()
    val workoutExercises: LiveData<WorkoutExercise> = mutableExercise

    private val mutableFinishListener = MutableLiveData<Boolean>()
    val finishListener: LiveData<Boolean> = mutableFinishListener

    fun getWorkout(workoutId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            mutableLoadingProgress.postValue(true)
            try {
                val workout = workoutInteractor.getWorkout(workoutId)
                mutableWorkoutWithExercises.postValue(workout)
            } catch (error: Throwable) {
                mutableErrorMessage.postValue(error.message)
            } finally {
                mutableLoadingProgress.postValue(false)
            }
        }
    }

    override fun addSetButtonClicked(workoutId: Long, exerciseId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val updatedExercise = workoutInteractor.createNewSet(workoutId, exerciseId)
            mutableExercise.postValue(updatedExercise)
        }
    }

    override fun updateReps(setId: Long, newRepCount: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            workoutInteractor.updateRepCount(setId, newRepCount)
        }
    }

    override fun updateWeight(setId: Long, weight: Double) {
        CoroutineScope(Dispatchers.IO).launch {
            workoutInteractor.updateWeight(setId, weight)
        }
    }

    fun finishCurrentWorkout() {
        CoroutineScope(Dispatchers.IO).launch {
            mutableWorkoutWithExercises.value?.run {
                mutableLoadingProgress.postValue(true)
                try {
                    workoutInteractor.finishWorkout(workoutId)
                    mutableFinishListener.postValue(true)
                } catch (error: Throwable) {
                    mutableErrorMessage.postValue(error.message)
                } finally {
                    mutableLoadingProgress.postValue(false)
                }
            }
        }
    }

    fun clearFinishLiveData() {
        mutableFinishListener.postValue(false)
    }
}
