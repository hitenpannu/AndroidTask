package com.hitenderpannu.auth.data.network.entity

data class NetworkUser(val _id:String, val name:String, val email:String)

data class LoginResponse(val user: NetworkUser, val token:String)

typealias SignupResponse = LoginResponse
