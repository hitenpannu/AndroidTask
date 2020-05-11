package com.hitenderpannu.feature_dashboard_ui

import android.app.Activity
import android.content.Intent

class DashBoardRouter(private val activity: Activity) {


    fun startDashBoard() {
        val intent = Intent(activity, DashBoardActivity::class.java)
        activity.startActivity(intent)
    }
}
