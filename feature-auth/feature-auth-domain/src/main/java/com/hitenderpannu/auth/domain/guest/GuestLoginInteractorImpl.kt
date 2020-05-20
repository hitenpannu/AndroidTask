package com.hitenderpannu.auth.domain.guest

import com.hitenderpannu.common.domain.UserPreferences
import java.util.*

class GuestLoginInteractorImpl(private val userPreferences: UserPreferences) :
    GuestLoginInteractor {

    override suspend fun login(): String {
        val guestId = UUID.randomUUID().toString()
        userPreferences.guestUserId = guestId
        return guestId
    }
}
