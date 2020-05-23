package com.hitenderpannu.body.composition.ui.landing_header

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.transition.TransitionManager
import com.hitenderpannu.body.composition.R
import com.hitenderpannu.body.composition.databinding.ViewBodyCompositionLandingHeaderBinding
import kotlin.properties.Delegates

class BodyCompositionLandingHeaderFragment : Fragment() {

    private lateinit var binding: ViewBodyCompositionLandingHeaderBinding
    private val headerConstraints by lazy {
        mapOf(
            Pair(false, ConstraintSet().apply { this.clone(binding.headerInfoConstraintLayout) }),
            Pair(true, ConstraintSet().apply { this.clone(context, R.layout.view_body_composition_landing_header_edit_mode) })
        )
    }
    private var isHeaderInEditMode: Boolean by Delegates.observable(false) { property, oldValue, newValue ->
        updateConstraints(newValue)
        updateListenersOnEditTexts(newValue)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ViewBodyCompositionLandingHeaderBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUpdateButton()
    }

    private fun initializeUpdateButton() {
        binding.updateButton.setOnClickListener { isHeaderInEditMode = !isHeaderInEditMode }
    }

    private fun updateConstraints(newValue: Boolean) {
        TransitionManager.beginDelayedTransition(binding.headerInfoConstraintLayout)
        headerConstraints[newValue]?.applyTo(binding.headerInfoConstraintLayout)
    }

    private fun updateListenersOnEditTexts(newValue: Boolean) {
        val tintColor = ColorStateList.valueOf(if (newValue) Color.WHITE else Color.TRANSPARENT)
        val group = arrayOf(binding.currentWeight, binding.currentFatPercentage, binding.muscleWeight)
        group.forEach {
            it.post {
                it.isEnabled = newValue
                it.backgroundTintList = tintColor
            }
        }
    }
}
