package com.hitenderpannu.workout.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.properties.Delegates

class UserSetupActivity : AppCompatActivity() {

    enum class Gender {
        MALE,
        FEMALE,
        UNKNOWN
    }

    private val topId = View.generateViewId();
    private val bottomId = View.generateViewId();

    private val maleIcon by lazy { findViewById<GenderSelectionButton>(R.id.icon_male) }
    private val femaleIcon by lazy { findViewById<GenderSelectionButton>(R.id.icon_female) }
    private val continueButton by lazy { findViewById<AppCompatButton>(R.id.button_continue) }
    private val agePickerView by lazy { findViewById<AgePickerView>(R.id.agePickerView) }
    private val constraintLayout by lazy { findViewById<ConstraintLayout>(R.id.genderSelectionConstraintLayout) }

    private var selectedGender: Gender by Delegates.observable(Gender.UNKNOWN) { property, oldValue, newValue ->
        if (oldValue == newValue) return@observable
        femaleIcon.isGenderSelected = newValue == Gender.FEMALE
        maleIcon.isGenderSelected = newValue == Gender.MALE
        continueButton.isEnabled = newValue != Gender.UNKNOWN
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gender_selection)
        setGenderClickListeners()
    }

    private fun setGenderClickListeners() {
        maleIcon.setOnClickListener {
            it.post { selectedGender = if (selectedGender != Gender.MALE) Gender.MALE else Gender.UNKNOWN }
        }

        femaleIcon.setOnClickListener {
            it.post { selectedGender = if (selectedGender != Gender.FEMALE) Gender.FEMALE else Gender.UNKNOWN }
        }

        continueButton.setOnClickListener {
            if(selectedGender == Gender.MALE) {
                femaleIcon.visibility = View.GONE
                maleIcon.animate().scaleY(0.06f).scaleX(1f)
                    .apply { this.duration = 300 }
                    .withEndAction {
                        maleIcon.alpha = 0f
                        maleIcon.post { maleIcon.visibility = View.GONE }
                        agePickerView.reveal()
                    }
                    .start()
                maleIcon.setImageDrawable(null)
            }else if(selectedGender == Gender.FEMALE) {
                maleIcon.visibility = View.GONE
                femaleIcon.animate().scaleY(0.06f).scaleX(1f)
                    .apply { this.duration = 300 }
                    .start()
                femaleIcon.setImageDrawable(null)
            }
        }
    }
}
