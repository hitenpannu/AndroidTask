package com.hitenderpannu.auth.data.network

import com.hitenderpannu.auth.entity.User

interface AuthRepo {

    suspend fun login(email: String, password: String): User

    suspend fun logout()

    suspend fun signup(name: String, email: String, password: String): User
}
