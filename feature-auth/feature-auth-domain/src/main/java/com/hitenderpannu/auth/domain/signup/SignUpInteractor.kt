package com.hitenderpannu.auth.domain.signup

import com.hitenderpannu.auth.entity.User

interface SignUpInteractor {
    suspend fun signUp(name: String, email: String, password: String, confirmPassword: String): User
}
