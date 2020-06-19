package com.hitenderpannu.taskapp.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitenderpannu.common.domain.UserPreferences
import com.hitenderpannu.taskapp.DynamicFeatureManager

class HomeViewModel(
    private val userPreferences: UserPreferences,
    private val dynamicFeatureManager: DynamicFeatureManager
) : ViewModel() {

    private val mutableUserName = MutableLiveData<String>()
    private val mutableUserEmail = MutableLiveData<String>()
    private val mutableListOfFeatures = MutableLiveData<List<DynamicFeatureManager.FEATURE>>()

    fun userName(): LiveData<String> = mutableUserName
    fun userEmail(): LiveData<String> = mutableUserEmail
    fun listOfFeatures(): LiveData<List<DynamicFeatureManager.FEATURE>> = mutableListOfFeatures

    init {

    }

    fun fetchUserData() {
        with(userPreferences) {
            mutableUserName.postValue(userName ?: "")
            mutableUserEmail.postValue(userEmail ?: "")
        }
    }
}
