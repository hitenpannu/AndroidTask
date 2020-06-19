package com.hitenderpannu.dynamictaskfeature.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hitenderpannu.dynamictaskfeature.databinding.ItemTaskBinding
import com.hitenderpannu.dynamictaskfeature.entity.Task


class TaskListAdapter(
    private var taskList: MutableList<Task> = mutableListOf()
) : RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder>() {

    enum class ViewType(val value: Int) {
        TASK(0)
    }

    private var adapterCallback: TaskListAdapterCallbackListener? = null

    fun attachCallbackListener(callbackListener: TaskListAdapterCallbackListener) {
        this.adapterCallback = callbackListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        return when (ViewType.values().first { it.value == viewType }) {
            ViewType.TASK -> {
                val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TaskListViewHolder.TaskItemViewHolder(binding);
            }
        }
    }

    override fun getItemCount(): Int = taskList.size

    override fun getItemViewType(position: Int): Int {
        return ViewType.TASK.value
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        when (holder) {
            is TaskListViewHolder.TaskItemViewHolder -> {
                with(taskList.get(position)) {
                    holder.bind(this) { newStatus -> adapterCallback?.updateTaskCompletionStatus(this, newStatus) }
                }
            }
        }
    }

    /* TODO use Diff utils to update only required views */
    fun updateTaskList(newList: List<Task>) {
        taskList.clear()
        taskList.addAll(newList)
        notifyDataSetChanged()
    }

    fun moveItem(fromPosition: Int, targetPosition: Int) {
        val fromItem = taskList[fromPosition]
        taskList.removeAt(fromPosition)
        taskList.add(targetPosition, fromItem)
        notifyItemMoved(fromPosition, targetPosition)
    }

    fun getItemDataAt(adapterPosition: Int): Task {
        return taskList[adapterPosition]
    }

    fun removeItemAt(adapterPosition: Int) {
        taskList.removeAt(adapterPosition)
        notifyItemRemoved(adapterPosition)
    }

    sealed class TaskListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        class TaskItemViewHolder(private val binding: ItemTaskBinding) : TaskListViewHolder(binding.root) {
            fun bind(task: Task, statusChangeCallback: (Boolean) -> Unit) {
                binding.taskDescription.text = task.description
                binding.taskStatus.setOnCheckedChangeListener(null)
                binding.taskStatus.isChecked = task.isCompleted
                binding.taskStatus.post {
                    binding.taskStatus.setOnCheckedChangeListener { buttonView, isChecked ->
                        if (task.isCompleted != isChecked) {
                            statusChangeCallback(isChecked)
                        }
                    }
                }
            }
        }
    }

    interface TaskListAdapterCallbackListener {
        fun updateTaskCompletionStatus(task: Task, newStatus: Boolean)
    }
}
