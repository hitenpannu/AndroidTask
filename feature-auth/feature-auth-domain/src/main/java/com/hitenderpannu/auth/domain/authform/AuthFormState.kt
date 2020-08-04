package com.hitenderpannu.auth.domain.authform

sealed class AuthFormState {
    object LoginState : AuthFormState()
    object SignUpState : AuthFormState()
    object GuestLoginInProcess: AuthFormState()
    data class LoginInProcess(val email: String, val password: String) : AuthFormState()
    data class SignUpInProcess(val userName:String, val email: String, val password: String) : AuthFormState()
    sealed class ErrorState : AuthFormState() {
        data class LoginError(val message: String) : ErrorState()
        data class SignUpError(val message: String) : ErrorState()
    }
}

sealed class AuthFormAction {
    object ToggleForm : AuthFormAction()
    object GuestLogin : AuthFormAction()
    data class StartAuth(val userName: String, val email: String, val password: String) : AuthFormAction()
    data class GotFormValidationError(val message: String) : AuthFormAction()
    data class GotAuthError(val message: String) : AuthFormAction()
}

sealed class AuthFormSideEffects {

}
