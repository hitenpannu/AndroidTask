package com.hitenderpannu.auth.domain.signup

import com.hitenderpannu.auth.data.network.AuthRepo
import com.hitenderpannu.auth.domain.errors.ConfirmPaswdDoestNotMatch
import com.hitenderpannu.auth.entity.User
import com.hitenderpannu.common.utils.NetworkConnectionChecker
import com.hitenderpannu.common.utils.NoInternetConnection

class SignUpInteractorImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val authRepo: AuthRepo) : SignUpInteractor {

    override suspend fun signUp(name: String, email: String, password: String, confirmPassword: String): User {
        if (password != confirmPassword) throw ConfirmPaswdDoestNotMatch
        if(!networkConnectionChecker.isConnected()){
            throw NoInternetConnection
        }
        return authRepo.signup(name, email, password)
    }
}
