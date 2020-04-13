package com.hitenderpannu.taskapp.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitenderpannu.common.domain.UserPreferences

class HomeViewModel(private val userPreferences: UserPreferences) : ViewModel() {

    private val mutableUserName = MutableLiveData<String>()
    private val mutableUserEmail = MutableLiveData<String>()

    fun userName(): LiveData<String> = mutableUserName
    fun userEmail(): LiveData<String> = mutableUserEmail

    fun fetchUserData(){
        with(userPreferences){
            mutableUserName.postValue(userName?:"")
            mutableUserEmail.postValue(userEmail?:"")
        }
    }
}
