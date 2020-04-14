package com.hitenderpannu.task.ui.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hitenderpannu.task.domain.TaskInteractor
import com.hitenderpannu.task.ui.taskFragment.TaskFragmentViewModel

class ViewModelFactory(
    private val taskInteractor: TaskInteractor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(
                TaskFragmentViewModel::class.java) -> TaskFragmentViewModel(
                taskInteractor
            ) as T
            else -> throw Throwable("UnSupported ViewModel")
        }
    }
}
