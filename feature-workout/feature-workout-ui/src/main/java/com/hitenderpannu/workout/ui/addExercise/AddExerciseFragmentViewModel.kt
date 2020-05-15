package com.hitenderpannu.workout.ui.addExercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitenderpannu.workout.domain.ExerciseListInteractor
import com.hitenderpannu.workout.entity.Exercise
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddExerciseFragmentViewModel(private val exerciseListInteractor: ExerciseListInteractor) : ViewModel(), SelectedExerciseStore {

    init {
        fetchListOfExercises()
    }

    private val mutableExerciseListProgress = MutableLiveData<Boolean>()
    val exerciseListProgress: LiveData<Boolean> = mutableExerciseListProgress

    private val mutableExerciseList = MutableLiveData<List<Exercise>>()
    val exerciseList: LiveData<List<Exercise>> = mutableExerciseList

    private val mutableErrorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = mutableErrorMessage

    private val listOfSelectedExercises = mutableListOf<Exercise>()

    private fun fetchListOfExercises() {
        CoroutineScope(Dispatchers.IO).launch {
            mutableExerciseListProgress.postValue(true)
            try {
                val list = exerciseListInteractor.getListOfAllExercises()
                mutableExerciseList.postValue(list)
            } catch (error: Throwable) {
                mutableErrorMessage.postValue(error.message)
            } finally {
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
}
