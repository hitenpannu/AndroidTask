package com.hitenderpannu.body.composition.ui.landing_header

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.transition.TransitionManager
import com.hitenderpannu.body.composition.R
import com.hitenderpannu.body.composition.databinding.ViewBodyCompositionLandingHeaderBinding
import com.hitenderpannu.body.composition.di.DaggerManager
import com.hitenderpannu.body.composition.entity.PrimaryBodyComposition
import javax.inject.Inject
import kotlin.properties.Delegates

class LandingHeaderFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: ViewBodyCompositionLandingHeaderBinding
    private val viewModel: LandingHeaderFragmentViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[LandingHeaderFragmentViewModel::class.java]
    }
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerManager.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ViewBodyCompositionLandingHeaderBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUpdateButton()
        viewModel.primaryBodyCompositionLiveData.observe(viewLifecycleOwner, latestCompositionObserver)
    }

    private fun initializeUpdateButton() {
        binding.updateButton.setOnClickListener {
            isHeaderInEditMode = !isHeaderInEditMode
            if (!isHeaderInEditMode) {
                viewModel.addNewCompositionIfRequired(
                    binding.currentWeight.text.toString(),
                    binding.currentFatPercentage.text.toString(),
                    binding.muscleWeight.text.toString()
                )
            }
        }
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

    private val latestCompositionObserver = Observer<PrimaryBodyComposition?> { latestComposition ->
        if (latestComposition == null) {
            binding.currentWeight.setText("0")
            binding.currentFatPercentage.setText("0")
            binding.muscleWeight.setText("0")
        } else {
            binding.currentWeight.setText(latestComposition.totalWeight.toPlainString())
            binding.currentFatPercentage.setText(latestComposition.fatPercentage.toPlainString())
            binding.muscleWeight.setText(latestComposition.muscleWeight.toPlainString())
        }
    }
}
