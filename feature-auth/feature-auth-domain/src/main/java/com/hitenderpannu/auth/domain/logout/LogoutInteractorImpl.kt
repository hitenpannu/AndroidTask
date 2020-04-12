package com.hitenderpannu.auth.domain.logout

import com.hitenderpannu.auth.data.network.AuthRepo
import com.hitenderpannu.common.domain.UserPreferences
import com.hitenderpannu.common.utils.NetworkConnectionChecker
import com.hitenderpannu.common.utils.NoInternetConnection

class LogoutInteractorImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val authRepo: AuthRepo,
    private val userPreferences: UserPreferences
) : LogoutInteractor {
    override suspend fun logout() {
        if (!networkConnectionChecker.isConnected()) {
            throw NoInternetConnection
        }
        authRepo.logout()
        clearUserPreferences()
    }

    private fun clearUserPreferences() {
        userPreferences.userId = null
        userPreferences.userName = null
        userPreferences.userEmail = null
        userPreferences.userToken = null
    }
}
