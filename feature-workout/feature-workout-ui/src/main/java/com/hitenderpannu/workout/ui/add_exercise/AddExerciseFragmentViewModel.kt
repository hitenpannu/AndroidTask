package com.hitenderpannu.workout.ui.add_exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitenderpannu.workout.domain.ApplyFilterListener
import com.hitenderpannu.workout.domain.ExerciseListInteractor
import com.hitenderpannu.workout.domain.WorkoutInteractor
import com.hitenderpannu.workout.entity.Exercise
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddExerciseFragmentViewModel(
    private val exerciseListInteractor: ExerciseListInteractor,
    private val workoutInteractor: WorkoutInteractor
) :
    ViewModel(), SelectedExerciseStore, ApplyFilterListener {

    init {
        exerciseListInteractor.setApplyFilterListener(this)
        fetchListOfExercises()
    }

    private val mutableExerciseListProgress = MutableLiveData<Boolean>()
    val exerciseListProgress: LiveData<Boolean> = mutableExerciseListProgress

    private val mutableExerciseList = MutableLiveData<List<Exercise>>()
    val exerciseList: LiveData<List<Exercise>> = mutableExerciseList

    private val mutableErrorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = mutableErrorMessage

    private val mutableNumberOfFiltersApplied = MutableLiveData<Int>()
    val numberOfFiltersApplied: LiveData<Int> = mutableNumberOfFiltersApplied

    private val mutableNewWorkoutId = MutableLiveData<Long>()
    val newWorkoutIdAvailable: LiveData<Long> = mutableNewWorkoutId

    private val listOfSelectedExercises = mutableListOf<Exercise>()

    fun fetchListOfExercises(fetchFresh: Boolean = false) {
        CoroutineScope(Dispatchers.IO).launch {
            mutableExerciseListProgress.postValue(true)
            try {
                val totalNumberOfFiltersApplied = exerciseListInteractor.getTotalNumberOfFiltersApplied()
                if (totalNumberOfFiltersApplied != 0) {
                    val currentExerciseList = exerciseListInteractor.getListOfAllExercises(fetchFresh)
                    val filteredList = currentExerciseList.filter { exercise ->
                        exercise.bodyParts.any { exerciseListInteractor.isAddedToFilters(it) } ||
                            exercise.equipments.any { exerciseListInteractor.isAddedToFilters(it) }
                    }
                    mutableExerciseList.postValue(filteredList)
                } else {
                    mutableExerciseList.postValue(exerciseListInteractor.getListOfAllExercises(fetchFresh))
                }
                mutableNumberOfFiltersApplied.postValue(totalNumberOfFiltersApplied)
            } catch (error: Throwable) {
                mutableErrorMessage.postValue(error.message)
            } finally {
                mutableExerciseListProgress.postValue(false)
            }
        }
    }

    fun createNewWorkout() {
        CoroutineScope(Dispatchers.IO).launch {
            mutableExerciseListProgress.postValue(true)
            try {
                val newWorkoutId = workoutInteractor.createNewWorkout(listOfSelectedExercises)
                mutableNewWorkoutId.postValue(newWorkoutId)
            } catch (error: Throwable) {
                mutableErrorMessage.postValue(error.message)
            } finally {
                mutableNewWorkoutId.postValue(null)
                mutableExerciseListProgress.postValue(false)
            }
        }
    }

    override fun addExercise(exercise: Exercise) {
        listOfSelectedExercises.add(exercise)
    }

    override fun isSelected(exercise: Exercise): Boolean {
        return listOfSelectedExercises.any { it.id == exercise.id }
    }

    override fun removeExercise(exercise: Exercise) {
        listOfSelectedExercises.remove(exercise)
    }

    override fun onFilterApplied() {
        fetchListOfExercises()
    }
}
