package com.hitenderpannu.auth.ui.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.hitenderpannu.auth.domain.signup.SignUpInteractor
import com.hitenderpannu.auth.entity.User
import com.hitenderpannu.common.utils.isEmailValid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupFragmentViewModel(private val signUpInteractor: SignUpInteractor) : ViewModel() {

    private val userName = MutableLiveData<String>()
    private val email = MutableLiveData<String>()
    private val password = MutableLiveData<String>()
    private val confirmPassword = MutableLiveData<String>()

    private val mutableUser = MutableLiveData<User>()
    private val mutableProgress = MutableLiveData<Boolean>()
    private val mutableSignUpError = MutableLiveData<String>()

    fun userLiveData(): LiveData<User> = mutableUser
    fun signUpProgress(): LiveData<Boolean> = mutableProgress
    fun signUpError(): LiveData<String> = mutableSignUpError

    val mutableUserNameError: LiveData<String?> = Transformations.map(userName, this::validateUserName)
    val mutableEmailError: LiveData<String?> = Transformations.map(email, this::validateEmail)
    val mutablePasswordError: LiveData<String?> = Transformations.map(password, this::validatePassword)
    val mutableConfirmPasswordError: LiveData<String?> = Transformations.map(confirmPassword, this::validateConfirmPassword)

    private val allErrors = MediatorLiveData<String?>().apply {
        addSource(mutableUserNameError) { this.value = if (isUserInputValid()) null else it }
        addSource(mutableEmailError) { this.value = if (isUserInputValid()) null else it }
        addSource(mutablePasswordError) { this.value = if (isUserInputValid()) null else it }
        addSource(mutableConfirmPasswordError) { this.value = if (isUserInputValid()) null else it }
    }

    val shouldEnableSignUpButton: LiveData<Boolean> = Transformations.map(allErrors) { error -> error == null }

    fun progress(): LiveData<Boolean> = mutableProgress
    fun updateUserName(userName: String) = this.userName.postValue(userName)
    fun updateEmail(userEmail: String) = this.email.postValue(userEmail)
    fun updateUserPassword(userPassword: String) = this.password.postValue(userPassword)
    fun updateUserConfirmPassword(confirmPassword: String) = this.confirmPassword.postValue(confirmPassword)

    private fun validateUserName(name: String): String? = if (name.isEmpty()) "Please enter username" else null

    private fun validateEmail(email: String?): String? {
        return if (email.isNullOrBlank()) "Please enter email"
        else if (!isEmailValid(email)) "Please enter valid email" else null
    }

    private fun validatePassword(password: String): String? {
        if (password.isBlank()) return "Please enter a password"
        if (password.length < 6) return "Password must be at least 6 character long"
        return null
    }

    private fun validateConfirmPassword(confirmPassword: String): String? {
        return if (confirmPassword != password.value) "Should match password above" else null
    }

    private fun invalidateSignUpButtonState() {
    }

    private fun isUserInputValid() = arrayOf(mutableUserNameError, mutableEmailError, mutablePasswordError, mutableConfirmPasswordError)
        .all { it.value == null }

    fun startSignUp() {
        if (!isUserInputValid()) return;
        mutableProgress.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val user = signUpInteractor.signUp(userName.value!!, email.value!!, password.value!!)
                mutableUser.postValue(user)
            } catch (error: Throwable) {
                Log.e("ERROR", error?.message ?: "")
                mutableSignUpError.postValue(error.message ?: "")
            } finally {
                mutableProgress.postValue(false)
            }
        }
    }
}
