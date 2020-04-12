package com.hitenderpannu.auth.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class AuthRouter(private val activity: AppCompatActivity) {

    fun startAuth() {
        val intent = Intent(activity, AuthActivity::class.java)
        activity.startActivity(intent)
    }
}
