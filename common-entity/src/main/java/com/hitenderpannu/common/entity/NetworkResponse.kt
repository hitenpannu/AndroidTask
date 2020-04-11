package com.hitenderpannu.common.entity

data class Status(val code: Int, val message: String)

data class NetworkResponse<T>(val status: Status, val data: T? = null)
