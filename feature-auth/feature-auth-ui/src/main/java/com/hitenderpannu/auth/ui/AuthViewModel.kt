package com.hitenderpannu.auth.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {

    val isAuthenticationDone = MutableLiveData<Boolean>()
}
