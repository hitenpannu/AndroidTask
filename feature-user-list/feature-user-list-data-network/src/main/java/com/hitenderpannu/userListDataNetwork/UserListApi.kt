package com.hitenderpannu.userListDataNetwork

import com.hitenderpannu.userListDataNetwork.entity.NetworkUser

interface UserListApi {

    suspend fun getUserList():List<NetworkUser>
}
