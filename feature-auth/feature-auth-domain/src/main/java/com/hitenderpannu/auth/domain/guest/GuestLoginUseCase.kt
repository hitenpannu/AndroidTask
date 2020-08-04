package com.hitenderpannu.auth.domain.guest

interface GuestLoginUseCase {
    suspend fun login() : String
}
