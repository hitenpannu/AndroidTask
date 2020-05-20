package com.hitenderpannu.common.domain

interface UserPreferences {
    var userId: String?
    var userName: String?
    var userEmail: String?
    var userToken: String?
    var guestUserId: String?

    fun isUserLoggedIn(): Boolean = userToken != null
}
