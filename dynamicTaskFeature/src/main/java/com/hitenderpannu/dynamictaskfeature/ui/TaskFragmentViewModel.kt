package com.hitenderpannu.dynamictaskfeature.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitenderpannu.dynamictaskfeature.domain.TaskInteractor
import com.hitenderpannu.dynamictaskfeature.entity.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskFragmentViewModel(
    private val taskInteractor: TaskInteractor
) : ViewModel(), TaskListAdapter.TaskListAdapterCallbackListener {

    private val mutableProgress = MutableLiveData<Boolean>()
    private val mutableTaskList = MutableLiveData<List<Task>>()
    private val mutableError = MutableLiveData<String>()

    fun liveTaskList(): LiveData<List<Task>> = mutableTaskList
    fun liveProgress(): LiveData<Boolean> = mutableProgress
    fun liveError(): LiveData<String> = mutableError

    init {
        getTaskList()
    }

    private suspend fun fetchTaskList() {
        val taskList = taskInteractor.getAllTasks()
        mutableTaskList.postValue(taskList)
    }

    private fun getTaskList() {
        CoroutineScope(Dispatchers.IO).launch {
            mutableProgress.postValue(true)
            try {
                fetchTaskList()
            } catch (error: Throwable) {
                mutableError.postValue(error.message ?: "Something went wrong")
            } finally {
                mutableProgress.postValue(false)
            }
        }
    }

    fun submittingNewTaskDescription(description: String) {
        if (description.isBlank()) return
        CoroutineScope(Dispatchers.IO).launch {
            mutableProgress.postValue(true)
            try {
                taskInteractor.createTask(description ?: "", false)
                fetchTaskList()
            } catch (error: Throwable) {
                mutableError.postValue(error.message ?: "Something went wrong")
            } finally {
                mutableProgress.postValue(false)
            }
        }
    }

    override fun updateTaskCompletionStatus(task: Task, newStatus: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            mutableProgress.postValue(true)
            try {
                taskInteractor.toggleCompletionStatus(task)
                fetchTaskList()
            } catch (error: Throwable) {
                mutableError.postValue(error.message ?: "Something went wrong")
            } finally {
                mutableProgress.postValue(false)
            }
        }
    }

    fun deleteTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                taskInteractor.deleteTask(task)
            } catch (error: Throwable) {
                mutableError.postValue(error.message ?: "Something went wrong")
            }
        }
    }
}
