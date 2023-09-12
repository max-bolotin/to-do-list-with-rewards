package com.dev.todolistwithrewards

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.todolistwithrewards.databinding.CompletedTasksHeaderBinding
import com.dev.todolistwithrewards.databinding.TaskItemCellBinding

class TaskItemAdapter(
    private val items: List<Any>, // List of TaskItem and CompletedTasksHeader
    private val clickListener: TaskItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_HEADER = 0
    private val VIEW_TYPE_ITEM = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val headerBinding = CompletedTasksHeaderBinding.inflate(inflater, parent, false)
                HeaderViewHolder(headerBinding)
            }

            VIEW_TYPE_ITEM -> {
                val itemBinding = TaskItemCellBinding.inflate(inflater, parent, false)
                TaskItemViewHolder(parent.context, itemBinding, clickListener)
            }

            else -> throw IllegalArgumentException("Invalid viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] is CompletedTasksHeader) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                val header = items[position] as CompletedTasksHeader
                holder.bind(header)
            }

            is TaskItemViewHolder -> {
                val taskItem = items[position] as TaskItem
                holder.bindTaskItem(taskItem)
            }
        }
    }

    inner class HeaderViewHolder(private val binding: CompletedTasksHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(header: CompletedTasksHeader) {
            binding.headerDateTextView.text = buildString {
                append("Your score for ")
                append(header.date.dayOfWeek.toString())
                append(", ")
                append(header.date.dayOfMonth)
                append(".")
                append(header.date.monthValue.toString())
                append(".")
                append(header.date.year.toString())
                append(":")
            }
            binding.headerScoreTextView.text = header.score.toString()
        }
    }
}
