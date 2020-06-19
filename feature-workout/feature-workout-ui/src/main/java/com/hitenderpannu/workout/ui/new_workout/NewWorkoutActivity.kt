package com.hitenderpannu.workout.ui.new_workout

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hitenderpannu.feature_dashboard_ui.R
import com.hitenderpannu.feature_dashboard_ui.databinding.FragmentNewWorkoutBinding
import com.hitenderpannu.workout.di.DaggerManager
import com.hitenderpannu.workout.entity.WorkoutExercise
import com.hitenderpannu.workout.entity.WorkoutWithExercises
import javax.inject.Inject

class NewWorkoutActivity : AppCompatActivity() {

    @Inject lateinit var adapter: NewWorkoutAdapter

    @Inject lateinit var factory: ViewModelProvider.Factory

    private lateinit var binding: FragmentNewWorkoutBinding

    private val viewModel by lazy {
        ViewModelProviders.of(this, factory)[NewWorkoutActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerManager.inject(this)
        binding = FragmentNewWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeView()
        handleCallingIntent()
    }

    private fun handleCallingIntent() {
        val workoutId = intent.getLongExtra(getString(R.string.argument_workout_id), -1)
        viewModel.getWorkout(workoutId)
    }

    private fun initializeView() {
        binding.exerciseListView.layoutManager = LinearLayoutManager(this)
        binding.exerciseListView.adapter = adapter
        adapter.attachExerciseCallback(viewModel)
        observeLiveData()
        binding.finishButton.setOnClickListener {
            viewModel.finishCurrentWorkout()
        }
    }

    private fun observeLiveData() {
        viewModel.errorMessage.observe(this, errorObserver)
        viewModel.loadingProgress.observe(this, loadingProgressObserver)
        viewModel.workoutWithExercises.observe(this, workoutObserver)
        viewModel.workoutExercises.observe(this, updatedExerciseObserver)
        viewModel.finishListener.observe(this, finishCompleteObserver)
    }

    private val loadingProgressObserver = Observer<Boolean> {
        binding.exerciseListView.isVisible = !it
        if (it) binding.progress.show() else binding.progress.hide()
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
            viewModel.clearFinishLiveData()
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}
