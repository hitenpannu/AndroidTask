package com.hitenderpannu.common.utils

interface NetworkConnectionChecker {

    suspend fun isConnected(): Boolean
}
