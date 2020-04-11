package com.hitenderpannu.auth.domain.login

import com.hitenderpannu.auth.entity.User

interface LoginInteractor {
    suspend fun login(email: String, password: String): User
}
