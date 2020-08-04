package com.hitenderpannu.auth.domain.signup

import com.hitenderpannu.auth.entity.User

interface SignUpUseCase {
    suspend fun signUp(name: String, email: String, password: String): User
}
