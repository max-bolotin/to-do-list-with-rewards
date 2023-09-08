package com.dev.todolistwithrewards

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import com.dev.todolistwithrewards.databinding.TaskItemCellBinding
import java.time.format.DateTimeFormatter

class TaskItemViewHolder(
    private val context: Context,
    private val binding: TaskItemCellBinding,
    private val clickListener: TaskItemClickListener
) : RecyclerView.ViewHolder(binding.root) {
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    fun bindTaskItem(taskItem: TaskItem) {
        binding.name.text = taskItem.name
        binding.score.text = taskItem.score.toString()
        binding.score.setTextColor(Color.parseColor("#FF07A61D"))

        if (taskItem.isCompleted()) {
            binding.name.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.score.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.dueTime.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }

        binding.completeButton.setImageResource(taskItem.imageResource())
        binding.completeButton.setColorFilter(taskItem.imageColor(context))

        binding.completeButton.setOnClickListener {
            clickListener.completeTaskItem(taskItem)
        }

        binding.taskCellContainer.setOnClickListener {
            clickListener.editTaskItem(taskItem)
        }

        if (taskItem.dueTime != null) {
            binding.dueTime.text = timeFormatter.format(taskItem.dueTime)
        } else
            binding.dueTime.text = ""
    }
}