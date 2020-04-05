package com.hitenderpannu.userListDataNetwork

import com.hitenderpannu.userlist.entity.User

public interface UserListRemoteRepo {

    suspend fun getUserList():List<User>
}
