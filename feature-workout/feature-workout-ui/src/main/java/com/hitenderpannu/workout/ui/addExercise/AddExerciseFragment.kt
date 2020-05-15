package com.hitenderpannu.workout.ui.addExercise

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hitenderpannu.feature_dashboard_ui.R
import com.hitenderpannu.feature_dashboard_ui.databinding.FragmentAddExerciseBinding
import com.hitenderpannu.workout.di.DaggerManager
import com.hitenderpannu.workout.entity.Exercise
import javax.inject.Inject

class AddExerciseFragment : Fragment() {

    @Inject
    lateinit var exerciseListAdapter: AddExerciseListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: AddExerciseFragmentViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(AddExerciseFragmentViewModel::class.java)
    }

    private lateinit var binding: FragmentAddExerciseBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerManager.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_exercise, container, false)
        binding = FragmentAddExerciseBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.exerciseListView.layoutManager = LinearLayoutManager(view.context)
        binding.exerciseListView.adapter = exerciseListAdapter
        exerciseListAdapter.attachStore(viewModel)
        binding.swipeRefresh.setOnRefreshListener { viewModel.fetchListOfExercises(true) }
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.errorMessage.observe(viewLifecycleOwner, errorMessageObserver)
        viewModel.exerciseListProgress.observe(viewLifecycleOwner, progressObserver)
        viewModel.exerciseList.observe(viewLifecycleOwner, exerciseListObserver)
    }

    private val progressObserver = Observer<Boolean> { show ->
        binding.addExerciseCloseButton.isEnabled = !show
        binding.addExerciseNextButton.isEnabled = !show
        binding.exerciseListView.isVisible = !show
        if (binding.swipeRefresh.isRefreshing) {
            binding.swipeRefresh.isRefreshing = show
        } else {
            if (show) binding.loadingProgress.show() else binding.loadingProgress.hide()
        }
    }

    private val errorMessageObserver = Observer<String?> { message ->
        if (message != null) {
            Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        }
    }
    private val exerciseListObserver = Observer<List<Exercise>> { exerciseList ->
        exerciseListAdapter.updateExerciseList(exerciseList)
    }
}
