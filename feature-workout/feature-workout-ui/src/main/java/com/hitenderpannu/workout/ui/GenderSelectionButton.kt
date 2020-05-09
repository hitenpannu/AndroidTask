package com.hitenderpannu.workout.ui

import android.content.Context
import android.graphics.drawable.TransitionDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.properties.Delegates

class GenderSelectionButton : AppCompatImageView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle)

    var isGenderSelected: Boolean by Delegates.observable(false ) { property, oldValue, newValue ->
        if(newValue) backgroundTransition?.startTransition(defaultAnimationDuration) else backgroundTransition?.resetTransition()
    }

    private var backgroundTransition: TransitionDrawable? = null

    private val defaultAnimationDuration = 300

    init {
        backgroundTransition = background as TransitionDrawable
    }
}
