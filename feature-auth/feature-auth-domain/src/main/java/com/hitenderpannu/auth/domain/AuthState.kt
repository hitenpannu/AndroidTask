package com.hitenderpannu.auth.domain

import com.hitenderpannu.auth.entity.User

sealed class AuthRequest {
    object Guest : AuthRequest()
    data class Login(val email: String, val password: String) : AuthRequest()
    data class SignUp(val userName: String, val email: String, val password: String) : AuthRequest()
}

sealed class AuthState {
    object Init : AuthState()
    data class Loading(val authRequest: AuthRequest) : AuthState()
    object Authenticated : AuthState()
    data class Failed(val error: Throwable) : AuthState()
}

sealed class AuthAction {
    data class Request(val authRequest: AuthRequest) : AuthAction()
    object AuthSuccess : AuthAction()
    data class AuthFailed(val error: Throwable) : AuthAction()
}

sealed class AuthSideEffects {

}
