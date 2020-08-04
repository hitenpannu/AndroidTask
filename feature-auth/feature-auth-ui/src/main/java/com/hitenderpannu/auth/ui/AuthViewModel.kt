package com.hitenderpannu.auth.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitenderpannu.auth.domain.AuthProcessInteractor
import com.hitenderpannu.auth.domain.AuthState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class AuthViewModel(private val authProcessInteractor: AuthProcessInteractor) : ViewModel() {

    private val mutableIsAuthenticationDone = MutableLiveData<Boolean>()
    val isAuthenticationDone: LiveData<Boolean> = mutableIsAuthenticationDone

    init {
        CoroutineScope(Dispatchers.IO).launch {
            authProcessInteractor.observerState()
                .filter { it is AuthState.Authenticated }
                .collect {
                    mutableIsAuthenticationDone.postValue(true)
                }
        }
    }
}
