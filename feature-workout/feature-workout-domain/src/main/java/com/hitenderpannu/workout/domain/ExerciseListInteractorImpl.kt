package com.hitenderpannu.workout.domain

import com.hitenderpannu.common.utils.NetworkConnectionChecker
import com.hitenderpannu.common.utils.NoInternetConnection
import com.hitenderpannu.workout.domain.local_repo.LocalBodyPartsRepo
import com.hitenderpannu.workout.domain.local_repo.LocalEquipmentsRepo
import com.hitenderpannu.workout.domain.local_repo.LocalExerciseRepo
import com.hitenderpannu.workout.domain.remote_repo.RemoteExerciseRepo
import com.hitenderpannu.workout.entity.BodyPart
import com.hitenderpannu.workout.entity.Equipment
import com.hitenderpannu.workout.entity.Exercise

class ExerciseListInteractorImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val remoteExerciseRepo: RemoteExerciseRepo,
    private val localExerciseRepo: LocalExerciseRepo,
    private val localBodyPartsRepo: LocalBodyPartsRepo,
    private val localEquipmentsRepo: LocalEquipmentsRepo
) : ExerciseListInteractor {

    private var listOfTempBodyFilters = mutableListOf<BodyPart>()
    private var listOfTempEquipmentFilters = mutableListOf<Equipment>()

    private var filterListener: ApplyFilterListener? = null

    override suspend fun getListOfAllExercises(fetchFresh: Boolean): List<Exercise> {
        var savedExercises = localExerciseRepo.getAllExercises()
        if (fetchFresh || savedExercises.isEmpty()) {
            if (!networkConnectionChecker.isConnected()) {
                throw NoInternetConnection
            }
            val newExercises = remoteExerciseRepo.getAllExercise()
            localExerciseRepo.insertExercises(newExercises)
        }
        savedExercises = localExerciseRepo.getAllExercises()
        return savedExercises
    }

    override suspend fun getListOfBodyParts(): List<BodyPart> {
        return localBodyPartsRepo.getAllBodyParts()
    }

    override suspend fun getListOfEquipments(): List<Equipment> {
        return localEquipmentsRepo.getAllEquipments()
    }

    override fun isAddedToFilters(bodyPart: BodyPart): Boolean {
        return listOfTempBodyFilters.any { it.id == bodyPart.id }
    }

    override fun isAddedToFilters(equipment: Equipment): Boolean {
        return listOfTempEquipmentFilters.any { it.id == equipment.id }
    }

    override fun applyFilters(bodyParts: List<BodyPart>, equipments: List<Equipment>) {
        listOfTempEquipmentFilters.clear()
        listOfTempBodyFilters.clear()

        listOfTempBodyFilters.addAll(bodyParts)
        listOfTempEquipmentFilters.addAll(equipments)
        filterListener?.onFilterApplied()
    }

    override fun getTotalNumberOfFiltersApplied(): Int {
        return listOfTempBodyFilters.size + listOfTempEquipmentFilters.size
    }

    override fun setApplyFilterListener(applyFilterListener: ApplyFilterListener) {
        filterListener = applyFilterListener
    }

    override fun clearFilters() {
        listOfTempBodyFilters.clear()
        listOfTempEquipmentFilters.clear()
        filterListener?.onFilterApplied()
    }
}
