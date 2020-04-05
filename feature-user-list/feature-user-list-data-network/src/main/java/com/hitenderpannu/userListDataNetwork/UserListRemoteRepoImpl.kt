package com.hitenderpannu.userListDataNetwork

import com.hitenderpannu.userlist.entity.User

class UserListRemoteRepoImpl (private val userListApi: UserListApi): UserListRemoteRepo {

    override suspend fun getUserList(): List<User> {
        return userListApi.getUserList().map { User(it.userId, it.email, it.name) };
    }
}
