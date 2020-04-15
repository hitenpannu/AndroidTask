package com.hitenderpannu.auth.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.hitenderpannu.auth.domain.login.LoginInteractor
import com.hitenderpannu.common.utils.isEmailValid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragmentViewModel(private val loginInteractor: LoginInteractor) : ViewModel() {

    private var mutableUserEmail = MutableLiveData<String>()
    private var mutableUserPassword = MutableLiveData<String>()
    private val mutableLoginProgress = MutableLiveData<Boolean>()
    private val mutableLoginSuccess = MutableLiveData<Boolean>()
    private val mutableLoginError = MutableLiveData<String>()

    val userEmailError: LiveData<String?> = Transformations.map(mutableUserEmail, this::validateUserEmail)
    val userPasswordError: LiveData<String?> = Transformations.map(mutableUserPassword, this::validatePassword)
    val shouldEnableLoginButton: LiveData<Boolean> = Transformations.map(pendingError()) { it == null }
    val loginProgress: LiveData<Boolean> = mutableLoginProgress
    val loginSuccess: LiveData<Boolean> = mutableLoginSuccess
    val loginError: LiveData<String> = mutableLoginError

    private fun pendingError() = MediatorLiveData<String?>().apply {
        addSource(userEmailError) { this.postValue(if (isAllInputValid()) null else it) }
        addSource(userPasswordError) { this.postValue(if (isAllInputValid()) null else it) }
    }

    private fun validateUserEmail(email: String): String? {
        return if (email.isBlank()) "Please enter email"
        else if (!isEmailValid(email)) "Please enter valid email" else null
    }

    private fun validatePassword(password: String): String? {
        if (password.isBlank()) return "Please enter a password"
        if (password.length < 6) return "Password must be at least 6 character long"
        return null
    }

    private fun isAllInputValid(): Boolean = userEmailError.value == null && userPasswordError.value == null

    fun updateUserEmail(email: String) = mutableUserEmail.postValue(email)
    fun updatePassword(password: String) = mutableUserPassword.postValue(password)

    fun startLoginProcess() {
        if (!isAllInputValid()) return
        CoroutineScope(Dispatchers.IO).launch {
            mutableLoginProgress.postValue(true)
            try {
                val loggedInUser = loginInteractor.login(mutableUserEmail.value ?: "", mutableUserPassword.value ?: "")
                mutableLoginSuccess.postValue(true)
            } catch (error: Throwable) {
                mutableLoginError.postValue(error.message ?: "Something went wrong")
            } finally {
                mutableLoginProgress.postValue(false)
            }
        }
    }
}
