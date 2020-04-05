package com.hitenderpannu.userListDataNetwork

import com.hitenderpannu.userlist.entity.User

public interface UserListRepo {

    suspend fun getUserList():List<User>
}
