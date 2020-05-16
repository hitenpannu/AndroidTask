package com.hitenderpannu.workout.ui.exercise_filters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialContainerTransform
import com.hitenderpannu.feature_dashboard_ui.R
import com.hitenderpannu.feature_dashboard_ui.databinding.FragmentFiltersBinding
import com.hitenderpannu.workout.di.DaggerManager
import com.hitenderpannu.workout.entity.BodyPart
import com.hitenderpannu.workout.entity.Equipment
import com.hitenderpannu.workout.ui.FilterChipView
import javax.inject.Inject

class ExerciseFiltersFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentFiltersBinding

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[ExerciseFilterFragmentViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerManager.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_filters, container, false)
        binding = FragmentFiltersBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listOfBodyParts.observe(viewLifecycleOwner, bodyPartsObserver)
        viewModel.listOfEquipments.observe(viewLifecycleOwner, equipmentsObserver)

        binding.actionApplyFilter.setOnClickListener {
            applyFilter()
        }
    }

    private fun applyFilter() {
        val selectedBodyParts = binding.bodyPartsContainer.children
            .filter { it is FilterChipView }
            .filter { (it as FilterChipView).isFilterSelected }
            .map { it.tag as BodyPart }
            .toList()

        val selectedEquipments = binding.equipmentsContainer.children
            .filter { it is FilterChipView }
            .filter { (it as FilterChipView).isFilterSelected }
            .map { it.tag as Equipment }
            .toList()

        viewModel.applyFilters(selectedBodyParts, selectedEquipments)

        findNavController().popBackStack()
    }

    private val bodyPartsObserver = Observer<List<Pair<BodyPart, Boolean>>> {
        binding.bodyPartsContainer.removeAllViews()
        it.map { part -> getItemChip(part.first.name, part.first.id, part.second).also { it.tag = part.first } }.forEach {
            binding.bodyPartsContainer.addView(it)
        }
    }

    private val equipmentsObserver = Observer<List<Pair<Equipment, Boolean>>> {
        binding.equipmentsContainer.removeAllViews()
        it.map { part -> getItemChip(part.first.name, part.first.id, part.second).also { it.tag = part.first} }.forEach {
            binding.equipmentsContainer.addView(it)
        }
    }

    private fun getItemChip(label: String, id: String, isSelected: Boolean): View {
        return FilterChipView(requireContext()).apply {
            setText(label, id)
            isFilterSelected = isSelected
        }
    }
}
