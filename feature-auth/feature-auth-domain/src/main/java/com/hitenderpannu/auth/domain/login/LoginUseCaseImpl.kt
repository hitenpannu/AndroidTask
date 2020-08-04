package com.hitenderpannu.auth.domain.login

import com.hitenderpannu.auth.data.network.AuthRepo
import com.hitenderpannu.auth.entity.User
import com.hitenderpannu.common.domain.UserPreferences
import com.hitenderpannu.common.utils.NetworkConnectionChecker
import com.hitenderpannu.common.utils.NoInternetConnection

class LoginUseCaseImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val authRepo: AuthRepo,
    private val userPreferences: UserPreferences
) : LoginUseCase {
    override suspend fun login(email: String, password: String): User {
        if (!networkConnectionChecker.isConnected()) {
            throw NoInternetConnection
        }
        return authRepo.login(email, password).also { updateUserPreferences(it) }
    }

    private fun updateUserPreferences(user: User) {
        userPreferences.userName = user.name
        userPreferences.userId = user.id
        userPreferences.userEmail = user.email
        userPreferences.userToken = user.authToken
        userPreferences.guestUserId = null
    }
}
