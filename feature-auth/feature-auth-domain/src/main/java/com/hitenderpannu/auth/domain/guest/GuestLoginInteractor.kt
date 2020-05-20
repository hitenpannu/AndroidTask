package com.hitenderpannu.auth.domain.guest

interface GuestLoginInteractor {
    suspend fun login() : String
}
