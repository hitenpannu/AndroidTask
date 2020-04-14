package com.hitenderpannu.task.ui.taskFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitenderpannu.task.domain.TaskInteractor
import com.hitenderpannu.task.entity.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskFragmentViewModel(
    private val taskInteractor: TaskInteractor
) : ViewModel() {

    private val mutableProgress = MutableLiveData<Boolean>()
    private val mutableTaskList = MutableLiveData<List<Task>>()
    private val mutableError = MutableLiveData<String>()

    fun liveTaskList(): LiveData<List<Task>> = mutableTaskList
    fun liveProgress(): LiveData<Boolean> = mutableProgress
    fun liveError(): LiveData<String> = mutableError

    init {
        getTaskList()
    }

    fun getTaskList() {
        CoroutineScope(Dispatchers.IO).launch {
            mutableProgress.postValue(true)
            try {
                val taskList = taskInteractor.getAllTasks()
                mutableTaskList.postValue(taskList)
            } catch (error: Throwable) {
                mutableError.postValue(error.message ?: "Something went wrong")
            } finally {
                mutableProgress.postValue(false)
            }
        }
    }
}
