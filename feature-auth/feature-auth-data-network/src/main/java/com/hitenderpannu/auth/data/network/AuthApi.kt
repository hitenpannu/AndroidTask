package com.hitenderpannu.auth.data.network

import com.hitenderpannu.auth.data.network.entity.LoginRequest
import com.hitenderpannu.auth.data.network.entity.LoginResponse
import com.hitenderpannu.auth.data.network.entity.SignUpRequest
import com.hitenderpannu.auth.data.network.entity.SignupResponse
import com.hitenderpannu.common.entity.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/login")
    suspend fun login(@Body loginRequest: LoginRequest): NetworkResponse<LoginResponse>

    @POST("/logout")
    suspend fun logout(): NetworkResponse<Nothing>

    @POST("/signup")
    suspend fun signup(@Body signUpRequest: SignUpRequest): NetworkResponse<SignupResponse>
}
