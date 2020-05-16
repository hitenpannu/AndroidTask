package com.hitenderpannu.workout.ui.exercise_filters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitenderpannu.workout.domain.ExerciseListInteractor
import com.hitenderpannu.workout.entity.BodyPart
import com.hitenderpannu.workout.entity.Equipment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExerciseFilterFragmentViewModel(
    private val exerciseListInteractor: ExerciseListInteractor
) : ViewModel() {

    private val mutableListOfBodyParts = MutableLiveData<List<Pair<BodyPart, Boolean>>>()
    val listOfBodyParts: LiveData<List<Pair<BodyPart, Boolean>>> = mutableListOfBodyParts

    private val mutableListOfEquipments = MutableLiveData<List<Pair<Equipment, Boolean>>>()
    val listOfEquipments: LiveData<List<Pair<Equipment, Boolean>>> = mutableListOfEquipments

    init {
        fetchAllBodyParts()
        fetchAllEquipments()
    }

    private fun fetchAllBodyParts() {
        CoroutineScope(Dispatchers.IO).launch {
            val bodyParts = exerciseListInteractor.getListOfBodyParts()
            mutableListOfBodyParts.postValue(bodyParts.map { Pair(it, exerciseListInteractor.isAddedToFilters(it)) })
        }
    }

    private fun fetchAllEquipments() {
        CoroutineScope(Dispatchers.IO).launch {
            val equipments = exerciseListInteractor.getListOfEquipments()
            mutableListOfEquipments.postValue(equipments.map { Pair(it, exerciseListInteractor.isAddedToFilters(it)) })
        }
    }

    fun applyFilters(selectedBodyParts: List<BodyPart>, selectedEquipments: List<Equipment>) {
        exerciseListInteractor.applyFilters(selectedBodyParts, selectedEquipments)
    }
}

