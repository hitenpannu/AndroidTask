package com.hitenderpannu.auth.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitenderpannu.auth.domain.login.LoginInteractor
import com.hitenderpannu.auth.domain.signup.SignUpInteractor
import com.hitenderpannu.auth.ui.R
import com.hitenderpannu.common.utils.isEmailValid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class AuthFragmentViewModel(
    private val loginInteractor: LoginInteractor,
    private val signUpInteractor: SignUpInteractor
) : ViewModel() {

    private enum class AUTH_MODE {
        LOGIN,
        SIGN_UP
    }

    private val mutableUserName = MutableLiveData<String>()
    private val mutableUserEmail = MutableLiveData<String>()
    private val mutableUserPassword = MutableLiveData<String>()
    private val mutableAuthProgress = MutableLiveData<Boolean>()
    private val mutableAuthSuccess = MutableLiveData<Boolean>()
    private val mutableAuthError = MutableLiveData<String>()

    private var selectedAuthMode: AUTH_MODE by Delegates.observable(AUTH_MODE.LOGIN) { property, oldValue, newValue ->
        when (newValue) {
            AUTH_MODE.LOGIN -> showUiForLogin()
            AUTH_MODE.SIGN_UP -> showUiForSignup()
        }
    }
    private val mutableUserNameError = MutableLiveData<String?>()
    val userNameError: LiveData<String?> = mutableUserNameError

    private val mutableUserEmailError = MutableLiveData<String?>()
    val userEmailError: LiveData<String?> = mutableUserEmailError

    private val mutablePasswordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?> = mutablePasswordError

    val authProgress: LiveData<Boolean> = mutableAuthProgress
    val authSuccess: LiveData<Boolean> = mutableAuthSuccess
    val authError: LiveData<String> = mutableAuthError

    private val mutableNameInputVisibility = MutableLiveData<Boolean>()
    val nameLayoutVisibilityLiveData: LiveData<Boolean> = mutableNameInputVisibility

    private val mutableAuthLabelResource = MutableLiveData<Int>()
    val authLabelResource: LiveData<Int> = mutableAuthLabelResource

    private val mutableAuthButtonActionResource = MutableLiveData<Int>()
    val authButtonActionResource: LiveData<Int> = mutableAuthButtonActionResource

    private val mutableCounterButtonActionResource = MutableLiveData<Int>()
    val counterButtonActionResource: LiveData<Int> = mutableCounterButtonActionResource

    private val mutableFocusedField = MutableLiveData<AuthFormField>()
    val focusedField: LiveData<AuthFormField> = mutableFocusedField

    private val mutableAuthButtonEnabled = MutableLiveData<Boolean>()
    val authButtonEnabled: LiveData<Boolean> = mutableAuthButtonEnabled

    fun updateUserName(name: String) = mutableUserName.postValue(name).also { updateAuthButtonEnableFlag() }
    fun updateUserEmail(email: String) = mutableUserEmail.postValue(email).also { updateAuthButtonEnableFlag() }
    fun updatePassword(password: String) = mutableUserPassword.postValue(password).also { updateAuthButtonEnableFlag() }

    fun toggleAuthMode() {
        selectedAuthMode = when (selectedAuthMode) {
            AUTH_MODE.LOGIN -> AUTH_MODE.SIGN_UP
            AUTH_MODE.SIGN_UP -> AUTH_MODE.LOGIN
        }
        updateAuthButtonEnableFlag()
    }

    private fun showUiForSignup() {
        mutableNameInputVisibility.postValue(true)
        mutableAuthLabelResource.postValue(R.string.label_lets_get_started)
        mutableAuthButtonActionResource.postValue(R.string.action_signup)
        mutableCounterButtonActionResource.postValue(R.string.action_already_have_account)
        mutableFocusedField.postValue(AuthFormField.USER_NAME)
    }

    private fun showUiForLogin() {
        mutableNameInputVisibility.postValue(false)
        mutableAuthLabelResource.postValue(R.string.label_welcome_back)
        mutableAuthButtonActionResource.postValue(R.string.action_login)
        mutableCounterButtonActionResource.postValue(R.string.do_not_have_account)
        mutableFocusedField.postValue(AuthFormField.EMAIL)
    }

    private fun updateAuthButtonEnableFlag() {
        val isUserNameEmpty = mutableUserName.value.isNullOrBlank()
        val isEmailEmpty = mutableUserEmail.value.isNullOrBlank()
        val isPasswordEmpty = mutableUserPassword.value.isNullOrBlank()

        val shouldEnableAuthButton = when (selectedAuthMode) {
            AUTH_MODE.LOGIN -> !isEmailEmpty && !isPasswordEmpty
            AUTH_MODE.SIGN_UP -> !isUserNameEmpty && !isEmailEmpty && !isPasswordEmpty
        }

        mutableAuthButtonEnabled.postValue(shouldEnableAuthButton)
    }

    fun startAuthProcess() {
        if (allFormFieldsAreValid()) {
            when (selectedAuthMode) {
                AUTH_MODE.LOGIN -> startLogin()
                AUTH_MODE.SIGN_UP -> startSignup()
            }
        }
    }

    private fun startLogin() {
        CoroutineScope(Dispatchers.IO).launch {
            mutableAuthProgress.postValue(true)
            try {
                val loggedInUser = loginInteractor.login(mutableUserEmail.value ?: "", mutableUserPassword.value ?: "")
                mutableAuthSuccess.postValue(true)
            } catch (error: Throwable) {
                mutableAuthError.postValue(error.message ?: "Something went wrong")
            } finally {
                mutableAuthProgress.postValue(false)
            }
        }
    }

    private fun startSignup() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val user = signUpInteractor.signUp(mutableUserName.value!!, mutableUserEmail.value!!, mutableUserPassword.value!!)
                mutableAuthSuccess.postValue(true)
            } catch (error: Throwable) {
                Log.e("ERROR", error?.message ?: "")
                mutableAuthError.postValue(error.message ?: "")
            } finally {
                mutableAuthProgress.postValue(false)
            }
        }
    }

    private fun allFormFieldsAreValid() : Boolean {
        var allFieldsAreValid = true
        if (selectedAuthMode == AUTH_MODE.SIGN_UP) {
            val userNameError = validateUserName(mutableUserName.value)
            mutableUserNameError.postValue(userNameError)
            allFieldsAreValid = allFieldsAreValid && userNameError.isNullOrBlank()
        }

        val emailError = validateUserEmail(mutableUserEmail.value)
        mutableUserEmailError.postValue(emailError)
        allFieldsAreValid = allFieldsAreValid && emailError.isNullOrBlank()

        val passwordError = validatePassword(mutableUserPassword.value)
        mutablePasswordError.postValue(passwordError)
        allFieldsAreValid = allFieldsAreValid && passwordError.isNullOrBlank()

        return allFieldsAreValid
    }

    private fun validateUserEmail(email: String?): String? {
        return if (email.isNullOrBlank()) "Please enter email"
        else if (!isEmailValid(email)) "Please enter valid email" else null
    }

    private fun validatePassword(password: String?): String? {
        if (password.isNullOrBlank()) return "Please enter a password"
        if (password.length < 6) return "Password must be at least 6 character long"
        return null
    }

    private fun validateUserName(name: String?): String? {
        return if (name.isNullOrBlank()) "Please enter email"
        else null
    }
}
