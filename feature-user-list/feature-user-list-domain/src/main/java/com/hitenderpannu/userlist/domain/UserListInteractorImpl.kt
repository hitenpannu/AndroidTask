package com.hitenderpannu.userlist.domain

import com.hitenderpannu.common.utils.NetworkConnectionChecker
import com.hitenderpannu.common.utils.NoInternetConnection
import com.hitenderpannu.userListDataNetwork.UserListRemoteRepo
import com.hitenderpannu.userlist.entity.User

public class UserListInteractorImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val remoteRepo: UserListRemoteRepo
) : UserListInteractor {
    override suspend fun getListOfAllUsers(): List<User> {
        if (!networkConnectionChecker.isConnected()) {
            throw NoInternetConnection
        }
        return remoteRepo.getUserList()
    }
}
