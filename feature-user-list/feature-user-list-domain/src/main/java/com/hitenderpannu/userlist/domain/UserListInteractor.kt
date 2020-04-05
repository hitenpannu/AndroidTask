package com.hitenderpannu.userlist.domain

import com.hitenderpannu.userlist.entity.User

interface UserListInteractor {
    @Throws(Throwable::class)
    suspend fun getListOfAllUsers(): List<User>
}
