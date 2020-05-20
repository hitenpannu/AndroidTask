package com.hitenderpannu.task.ui.taskFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hitenderpannu.task.entity.Task
import com.hitenderpannu.task.ui.databinding.FragmentTaskBinding
import com.hitenderpannu.task.ui.di.DaggerManager
import com.hitenderpannu.task.ui.util.ViewModelFactory
import javax.inject.Inject

class TaskFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var taskListAdapter: TaskListAdapter

    private val viewModel: TaskFragmentViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[TaskFragmentViewModel::class.java]
    }

    private var binding: FragmentTaskBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTaskBinding.inflate(inflater)
        DaggerManager.inject(this)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.taskListView?.layoutManager = LinearLayoutManager(context)
        binding?.taskListView?.adapter = taskListAdapter
        binding?.addNewTask?.onSubmittingTaskDescription { viewModel.submittingNewTaskDescription(it) }
        taskListAdapter.attachCallbackListener(viewModel)
        viewModel.liveTaskList().observe(viewLifecycleOwner, listObserver)
        viewModel.liveProgress().observe(viewLifecycleOwner, progressObserver)
        viewModel.liveError().observe(viewLifecycleOwner, errorObserver)
    }

    private val listObserver = Observer<List<Task>> {
        taskListAdapter.updateTaskList(it)
        binding?.taskListView?.post {
            binding?.taskListView?.smoothScrollToPosition(it.size)
        }
    }

    private val progressObserver = Observer<Boolean> { show ->
        if (show) binding?.taskListProgress?.show() else binding?.taskListProgress?.hide()
    }

    private val errorObserver = Observer<String> { errorMessage ->
        Snackbar.make(binding!!.root, errorMessage, Snackbar.LENGTH_INDEFINITE).show()
    }

    override fun onDestroyView() {
        binding = null
        viewModel.liveTaskList().removeObserver(listObserver)
        super.onDestroyView()
    }
}
