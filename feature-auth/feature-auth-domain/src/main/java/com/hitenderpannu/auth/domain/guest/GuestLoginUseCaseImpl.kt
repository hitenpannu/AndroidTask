package com.hitenderpannu.auth.domain.guest

import com.hitenderpannu.common.domain.UserPreferences
import java.util.*

class GuestLoginUseCaseImpl(private val userPreferences: UserPreferences) :
    GuestLoginUseCase {

    override suspend fun login(): String {
        val guestId = UUID.randomUUID().toString()
        userPreferences.guestUserId = guestId
        return guestId
    }
}
