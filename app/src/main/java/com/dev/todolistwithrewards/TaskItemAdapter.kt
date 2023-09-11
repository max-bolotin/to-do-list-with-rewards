package com.dev.todolistwithrewards

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.todolistwithrewards.databinding.TaskItemCellBinding

class TaskItemAdapter(
    private val taskItems: MutableList<TaskItem>,
    private val clickListener: TaskItemClickListener
) : RecyclerView.Adapter<TaskItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = TaskItemCellBinding.inflate(from, parent, false)
        return TaskItemViewHolder(parent.context, binding, clickListener)
    }

    fun getTaskItem(position: Int): TaskItem? {
        if (position >= 0 && position < taskItems.size) {
            return taskItems[position]
        }
        return null
    }

    override fun getItemCount() = taskItems.size

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        holder.bindTaskItem(taskItems[position])
    }

    fun deleteTask(position: Int) {
        // Remove the task from the list if it's a valid position
        if (position >= 0 && position < taskItems.size) {
            taskItems.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}