package com.hitenderpannu.base.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.annotation.RequiresApi

interface ConnectionManager {
    fun isConnected(): Boolean
}

class ConnectionManagerFactory(private val context: Context) {
    fun getConnectionManager(): ConnectionManager {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NetworkConnectionManager(manager)
        } else {
            NetworkConnectionManagerBLWN(manager)
        }
    }

    private inner class NetworkConnectionManagerBLWN(private val manager: ConnectivityManager) : ConnectionManager {
        override fun isConnected(): Boolean {
            return manager.activeNetworkInfo?.isConnectedOrConnecting ?: false
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private inner class NetworkConnectionManager(private val manager: ConnectivityManager) : ConnectionManager {

        private var isConnectedToNetwork = false

        private val networkCallback: ConnectivityManager.NetworkCallback by lazy {
            buildNetworkCallback()
        }

        init {
            registerForCallback()
        }

        private fun registerForCallback() {
            isConnectedToNetwork = manager.activeNetwork != null
            manager.registerDefaultNetworkCallback(networkCallback)
        }

        private fun buildNetworkCallback(): ConnectivityManager.NetworkCallback {
            return object : ConnectivityManager.NetworkCallback() {
                override fun onLost(network: Network) {
                    super.onLost(network)
                    isConnectedToNetwork = false
                }

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    isConnectedToNetwork = true
                }
            }
        }

        override fun isConnected() = isConnectedToNetwork
    }
}
