package com.hitenderpannu.workout.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView

class UserSetupActivity : AppCompatActivity() {

    enum class Gender {
        MALE,
        FEMALE,
        UNKNOWN
    }

    private var selectedGender: Gender = Gender.UNKNOWN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_setup)
        setGenderClickListeners()
    }

    private fun setGenderClickListeners() {
    }
}
