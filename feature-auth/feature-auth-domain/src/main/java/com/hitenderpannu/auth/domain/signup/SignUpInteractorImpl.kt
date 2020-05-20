package com.hitenderpannu.auth.domain.signup

import com.hitenderpannu.auth.data.network.AuthRepo
import com.hitenderpannu.auth.entity.User
import com.hitenderpannu.common.domain.UserPreferences
import com.hitenderpannu.common.utils.NetworkConnectionChecker
import com.hitenderpannu.common.utils.NoInternetConnection

class SignUpInteractorImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val authRepo: AuthRepo,
    private val userPreferences: UserPreferences
) : SignUpInteractor {

    override suspend fun signUp(name: String, email: String, password: String): User {
        if (!networkConnectionChecker.isConnected()) {
            throw NoInternetConnection
        }
        return authRepo.signup(name, email, password).also { updateUserPreferences(it) }
    }

    private fun updateUserPreferences(user: User) {
        userPreferences.userId = user.id
        userPreferences.userName = user.name
        userPreferences.userEmail = user.email
        userPreferences.userToken = user.authToken
        userPreferences.guestUserId = null
    }
}
