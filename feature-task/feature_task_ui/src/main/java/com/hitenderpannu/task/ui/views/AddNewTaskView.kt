package com.hitenderpannu.task.ui.views

import android.content.Context
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.hitenderpannu.task.ui.R
import kotlin.properties.Delegates

class AddNewTaskView : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle)

    enum class MODE {
        EXPANDED,
        COLLAPSED
    }

    private val constraintsMap = mutableMapOf<MODE, ConstraintSet>()
    private var currentMode: MODE by Delegates.observable(MODE.COLLAPSED) { property, oldValue, newValue ->
        updateUi(newValue)
    }

    private var constraintLayout: ConstraintLayout
    private var onSubmittingDescription: (String) -> Unit = {}

    init {
        View.inflate(context, R.layout.view_new_task_1, this)
        constraintLayout = findViewById(R.id.newTaskContainer)
        constraintsMap[MODE.COLLAPSED] = ConstraintSet().apply { this.clone(constraintLayout) }
        constraintsMap[MODE.EXPANDED] = ConstraintSet().apply { this.clone(context, R.layout.view_new_task_2) }

        initializeListeners()
    }

    private fun initializeListeners() {
        findViewById<AppCompatButton>(R.id.addNewTaskButton).setOnClickListener {
            currentMode = when (currentMode) {
                MODE.EXPANDED -> {
                    with(findViewById<AppCompatEditText>(R.id.newTaskDescriptionEditText)) {
                        onSubmittingDescription(text.toString())
                        setText("")
                    }
                    MODE.COLLAPSED
                }
                MODE.COLLAPSED -> MODE.EXPANDED
            }
        }
    }

    private fun updateUi(newValue: MODE) {
        TransitionManager.beginDelayedTransition(this)
        when (newValue) {
            MODE.EXPANDED -> constraintsMap[MODE.EXPANDED]?.applyTo(constraintLayout)
            MODE.COLLAPSED -> constraintsMap[MODE.COLLAPSED]?.applyTo(constraintLayout)
        }
    }

    fun onSubmittingTaskDescription(callback: (String) -> Unit) {
        onSubmittingDescription = callback
    }
}
