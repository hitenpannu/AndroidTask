package com.hitenderpannu.auth.ui.authform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitenderpannu.auth.domain.authform.AuthFormInteractor
import com.hitenderpannu.auth.domain.authform.AuthFormState
import com.hitenderpannu.auth.ui.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AuthFragmentViewModel(
    private val authFormInteractor: AuthFormInteractor
) : ViewModel() {

    private val mutableUserName = MutableLiveData<String>()
    private val mutableUserEmail = MutableLiveData<String>()
    private val mutableUserPassword = MutableLiveData<String>()
    private val mutableAuthProgress = MutableLiveData<Boolean>()
    private val mutableAuthError = MutableLiveData<String>()

    private val mutableFormFieldError: MutableLiveData<String?> = MutableLiveData()
    val formFieldError: LiveData<String?> = mutableFormFieldError

    val authProgress: LiveData<Boolean> = mutableAuthProgress
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

    fun updateUserName(name: String) = mutableUserName.postValue(name)
    fun updateUserEmail(email: String) = mutableUserEmail.postValue(email)
    fun updatePassword(password: String) = mutableUserPassword.postValue(password)

    init {
        CoroutineScope(Dispatchers.IO).launch {
            authFormInteractor.observeState()
                .collect { handleAuthState(it) }
        }
    }

    fun toggleAuthMode() {
        authFormInteractor.toggleForm()
    }

    private fun showUiForSignup() {
        mutableNameInputVisibility.postValue(true)
        mutableAuthLabelResource.postValue(R.string.label_lets_get_started)
        mutableAuthButtonActionResource.postValue(R.string.action_signup)
        mutableCounterButtonActionResource.postValue(R.string.action_already_have_account)
        mutableFocusedField.postValue(AuthFormField.USER_NAME)
        mutableAuthProgress.postValue(false)
    }

    private fun showUiForLogin() {
        mutableNameInputVisibility.postValue(false)
        mutableAuthLabelResource.postValue(R.string.label_welcome_back)
        mutableAuthButtonActionResource.postValue(R.string.action_login)
        mutableCounterButtonActionResource.postValue(R.string.do_not_have_account)
        mutableFocusedField.postValue(AuthFormField.EMAIL)
        mutableAuthProgress.postValue(false)
    }

    fun startAuthProcess() {
        authFormInteractor.performAuthentication(
            mutableUserName.value ?: "",
            mutableUserEmail.value ?: "",
            mutableUserPassword.value ?: ""
        )
    }

    private fun handleAuthState(authState: AuthFormState) {
        when (authState) {
            AuthFormState.LoginState -> showUiForLogin()
            AuthFormState.SignUpState -> showUiForSignup()
            AuthFormState.GuestLoginInProcess,
            is AuthFormState.LoginInProcess,
            is AuthFormState.SignUpInProcess -> mutableAuthProgress.postValue(true)
            is AuthFormState.ErrorState -> handleError(authState)
        }
    }

    private fun handleError(authState: AuthFormState.ErrorState) {
        mutableAuthProgress.postValue(false)
        when (authState) {
            is AuthFormState.ErrorState.LoginError -> mutableAuthError.postValue(authState.message)
            is AuthFormState.ErrorState.SignUpError -> mutableAuthError.postValue(authState.message)
        }
    }

    fun startGuestLogin() {
        authFormInteractor.performGuestLogin()
    }
}
