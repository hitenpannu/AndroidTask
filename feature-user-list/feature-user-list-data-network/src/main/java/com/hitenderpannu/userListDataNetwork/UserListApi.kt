package com.hitenderpannu.userListDataNetwork

import com.hitenderpannu.userListDataNetwork.entity.NetworkUser
import retrofit2.http.GET

interface UserListApi {

    @GET("/users")
    suspend fun getUserList():List<NetworkUser>
}
