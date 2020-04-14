package com.hitenderpannu.task.ui.taskFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hitenderpannu.task.entity.Task
import com.hitenderpannu.task.ui.databinding.ItemTaskBinding

class TaskListAdapter(private var taskList: List<Task> = listOf()) : RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskListViewHolder(binding);
    }

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    /* TODO use Diff utils to update only required views */
    fun updateTaskList(newList: List<Task>) {
        taskList = newList
        notifyDataSetChanged()
    }

    inner class TaskListViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.taskDescription.text = task.description
            binding.taskStatus.isChecked = task.completed
        }
    }
}
