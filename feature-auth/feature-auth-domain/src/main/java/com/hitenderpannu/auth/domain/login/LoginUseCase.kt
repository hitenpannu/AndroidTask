package com.hitenderpannu.auth.domain.login

import com.hitenderpannu.auth.entity.User

interface LoginUseCase {
    suspend fun login(email: String, password: String): User
}
