package com.hitenderpannu.auth.domain.login

import com.hitenderpannu.auth.data.network.AuthRepo
import com.hitenderpannu.auth.entity.User
import com.hitenderpannu.common.utils.NetworkConnectionChecker
import com.hitenderpannu.common.utils.NoInternetConnection

class LoginInteractorImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val authRepo: AuthRepo
) : LoginInteractor {
    override suspend fun login(email: String, password: String): User {
        if (!networkConnectionChecker.isConnected()) {
            throw NoInternetConnection
        }
        return authRepo.login(email, password)
    }
}
