package com.hitenderpannu.auth.domain.authform

import kotlinx.coroutines.flow.Flow

interface AuthFormInteractor {

    fun observeState(): Flow<AuthFormState>

    fun toggleForm()

    fun performGuestLogin()

    fun performAuthentication(username:String, email: String, password:String)
}
