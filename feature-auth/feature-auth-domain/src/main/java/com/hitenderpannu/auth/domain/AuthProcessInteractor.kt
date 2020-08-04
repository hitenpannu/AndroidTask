package com.hitenderpannu.auth.domain

import kotlinx.coroutines.flow.Flow
import java.util.*

interface AuthProcessInteractor {

    fun observerState(): Flow<AuthState>

    fun processRequest(authRequest: AuthRequest)
}
