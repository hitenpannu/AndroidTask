package com.hitenderpannu.workout.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.hitenderpannu.base.BaseFragment
import com.hitenderpannu.feature_dashboard_ui.R
import com.hitenderpannu.feature_dashboard_ui.databinding.FragmentDashBoardsBinding
import com.hitenderpannu.workout.di.DaggerManager
import com.hitenderpannu.workout.entity.Workout
import com.hitenderpannu.workout.ui.new_workout.NewWorkoutFragment
import javax.inject.Inject

class DashBoardFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentDashBoardsBinding

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[DashboardFragmentViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerManager.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dash_boards, container, false)
        binding = FragmentDashBoardsBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.createFirstWorkoutButton.setOnClickListener {
            findNavController().navigate(R.id.action_dashBoardFragment_to_addExerciseFragment)
        }
        viewModel.unFinishedWorkout.observe(viewLifecycleOwner, unfinishedWorkoutObserver)
    }

    private val unfinishedWorkoutObserver = Observer<Workout?> { workout ->
        binding.unFinishedWorkoutCard.isVisible = workout != null
        binding.actionContinue.setOnClickListener {
            if (workout != null) {
                val bundle = bundleOf(NewWorkoutFragment.KEY_WORKOUT_ID to workout.workoutId)
                findNavController().navigate(R.id.action_dashBoardFragment_to_newWorkoutFragment, bundle)
            }
        }
    }
}
