package com.hitenderpannu.workout.ui.new_workout

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hitenderpannu.feature_dashboard_ui.R
import com.hitenderpannu.feature_dashboard_ui.databinding.FragmentNewWorkoutBinding
import com.hitenderpannu.workout.di.DaggerManager
import com.hitenderpannu.workout.entity.WorkoutExercise
import com.hitenderpannu.workout.entity.WorkoutWithExercises
import javax.inject.Inject

class NewWorkoutFragment : Fragment() {

    companion object {
        const val KEY_WORKOUT_ID = "workout_id"
    }

    @Inject lateinit var adapter: NewWorkoutAdapter

    @Inject lateinit var factory: ViewModelProvider.Factory

    private lateinit var binding: FragmentNewWorkoutBinding

    private val viewModel by lazy {
        ViewModelProviders.of(this, factory)[NewWorkoutFragmentViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerManager.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_new_workout, container, false)
        binding = FragmentNewWorkoutBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.exerciseListView.layoutManager = LinearLayoutManager(requireContext())
        binding.exerciseListView.adapter = adapter
        adapter.attachExerciseCallback(viewModel)
        observeLiveData()
        arguments?.getLong(KEY_WORKOUT_ID)?.let {
            viewModel.getWorkout(it)
        }
        binding.finishButton.setOnClickListener {
            viewModel.finishCurrentWorkout()
        }
    }

    private fun observeLiveData() {
        viewModel.errorMessage.observe(viewLifecycleOwner, errorObserver)
        viewModel.loadingProgress.observe(viewLifecycleOwner, loadingProgressObserver)
        viewModel.workoutWithExercises.observe(viewLifecycleOwner, workoutObserver)
        viewModel.workoutExercises.observe(viewLifecycleOwner, updatedExerciseObserver)
        viewModel.finishListener.observe(viewLifecycleOwner, finishCompleteObserver)
    }

    private val loadingProgressObserver = Observer<Boolean> {
        binding.exerciseListView.isVisible = !it
        if(it) binding.progress.show() else binding.progress.hide()
    }

    private val errorObserver = Observer<String?> {
        if (it != null) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    private val workoutObserver = Observer<WorkoutWithExercises> {
        adapter.updateWorkout(it)
    }

    private val updatedExerciseObserver = Observer<WorkoutExercise> {
        adapter.updateExercise(it)
    }

    private val finishCompleteObserver = Observer<Boolean> {
        if (it) {
            binding.finishButton.visibility = View.GONE
            findNavController().popBackStack()
        }
    }
}
