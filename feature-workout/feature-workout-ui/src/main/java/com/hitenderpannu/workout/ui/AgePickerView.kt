package com.hitenderpannu.workout.ui

import android.content.Context
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

class AgePickerView : FrameLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle)

    private lateinit var constraintLayout : ConstraintLayout
    init {
        View.inflate(context, R.layout.view_age_picker_step1, this)
        constraintLayout = findViewById<ConstraintLayout>(R.id.constraintAgePicker)
    }

    fun reveal() {
        val startingConstraint = ConstraintSet().apply { clone(constraintLayout) }
        val finishConstraint = ConstraintSet().apply { clone(context, R.layout.view_age_picker_step2) }

        TransitionManager.beginDelayedTransition(constraintLayout)
        finishConstraint.applyTo(constraintLayout)
    }
}
