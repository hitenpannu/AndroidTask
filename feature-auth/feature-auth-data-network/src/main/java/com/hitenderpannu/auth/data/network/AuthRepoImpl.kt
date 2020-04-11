package com.hitenderpannu.auth.data.network

import com.hitenderpannu.auth.entity.User
import com.hitenderpannu.common.entity.StatusCode

class AuthRepoImpl(private val authApi: AuthApi) : AuthRepo {

    override suspend fun login(email: String, password: String): User {
        val networkResponse = authApi.login(email, password)
        val loginResponse = networkResponse.data ?: throw Exception(networkResponse.status.message)
        return User(loginResponse.user._id, loginResponse.user.name, loginResponse.user.email, loginResponse.token)
    }

    override suspend fun logout() {
        val networkResponse = authApi.logout()
        if (networkResponse.status.code != StatusCode.SUCCESS.code) {
            throw Exception(networkResponse.status.message)
        }
    }

    override suspend fun signup(name: String, email: String, password: String): User {
        val networkResponse = authApi.signup(name, email, password)
        val signupResponse = networkResponse.data ?: throw Exception(networkResponse.status.message)
        return User(signupResponse.user._id, signupResponse.user.name, signupResponse.user.email, signupResponse.token)
    }
}
