package com.hitenderpannu.taskapp.home

import android.app.Activity
import android.content.Intent

class HomeRouter(private val activity: Activity) {

    fun startHome(finishCurrent: Boolean) {
        val intent = Intent(activity, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(intent)
        if (finishCurrent) activity.finish()
    }
}
