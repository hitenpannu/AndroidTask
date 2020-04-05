package com.hitenderpannu.userListDataNetwork

import com.hitenderpannu.userlist.entity.User

class UserListRepoImpl (private val userListApi: UserListApi): UserListRepo {

    override suspend fun getUserList(): List<User> {
        return userListApi.getUserList().map { User(it.userId, it.email, it.name) };
    }
}
