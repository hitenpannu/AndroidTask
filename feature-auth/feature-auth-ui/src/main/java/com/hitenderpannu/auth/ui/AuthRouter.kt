package com.hitenderpannu.auth.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class AuthRouter(private val activity: AppCompatActivity) {

    companion object {
        const val REQUEST_AUTHENTICATION = 1000
    }

    fun startAuth() {
        val intent = Intent(activity, AuthActivity::class.java)
        activity.startActivityForResult(intent, REQUEST_AUTHENTICATION)
    }
}
