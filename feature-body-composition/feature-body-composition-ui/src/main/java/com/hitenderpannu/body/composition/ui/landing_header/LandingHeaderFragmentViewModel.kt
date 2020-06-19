package com.hitenderpannu.body.composition.ui.landing_header

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitenderpannu.body.composition.domain.BodyCompositionInteractor
import com.hitenderpannu.body.composition.entity.PrimaryBodyComposition
import com.hitenderpannu.common.entity.WeightUnit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LandingHeaderFragmentViewModel(
    private val bodyCompositionInteractor: BodyCompositionInteractor
) : ViewModel() {

    private val mutablePrimaryBodyComposition = MutableLiveData<PrimaryBodyComposition?>()
    val primaryBodyCompositionLiveData: LiveData<PrimaryBodyComposition?> = mutablePrimaryBodyComposition

    private val mutableErrorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = mutableErrorMessage

    init {
        getLatestBodyComposition()
    }

    private fun getLatestBodyComposition() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                bodyCompositionInteractor.getLatestEntry().collect {latestComposition ->
                    mutablePrimaryBodyComposition.postValue(latestComposition)
                }
            } catch (error: Throwable) {
                mutableErrorMessage.postValue(error.message)
            }
        }
    }

    fun addNewCompositionIfRequired(currentWeight: String, fatPercentage: String, muscleMass: String) {
        if (checkForErrors(currentWeight, fatPercentage, muscleMass)) return
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val entity = PrimaryBodyComposition(
                    totalWeight = currentWeight.toBigDecimal(),
                    fatPercentage = fatPercentage.toBigDecimal(),
                    muscleWeight = muscleMass.toBigDecimal(),
                    createdOn = System.currentTimeMillis(),
                    weightUnit = WeightUnit.KG
                )
                bodyCompositionInteractor.addNewEntry(entity)
            } catch (error: Throwable) {
                mutableErrorMessage.postValue(error.message)
            }
        }
    }

    private fun checkForErrors(currentWeight: String, fatPercentage: String, muscleMass: String): Boolean {
        if (currentWeight.isBlank()) {
            mutableErrorMessage.postValue("Please Provide Current Weight")
            return true
        }
        if (fatPercentage.isBlank()) {
            mutableErrorMessage.postValue("Please Provide Fat percentage")
            return true
        }
        if (muscleMass.isBlank()) {
            mutableErrorMessage.postValue("Please Provide Muscle Mass")
            return true
        }
        if (primaryBodyCompositionLiveData.value != null) {
            val isCurrentWeightSame = currentWeight == primaryBodyCompositionLiveData.value!!.totalWeight.toPlainString()
            val isFatPercentageSame = fatPercentage == primaryBodyCompositionLiveData.value!!.fatPercentage.toPlainString()
            val isMuscleMassSame = muscleMass == primaryBodyCompositionLiveData.value!!.muscleWeight.toPlainString()
            if (isCurrentWeightSame && isFatPercentageSame && isMuscleMassSame) {
                return true
            }
        }
        return false
    }
}
