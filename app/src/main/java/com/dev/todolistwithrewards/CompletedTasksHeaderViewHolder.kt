package com.dev.todolistwithrewards

import androidx.recyclerview.widget.RecyclerView
import com.dev.todolistwithrewards.databinding.CompletedTasksHeaderBinding

class CompletedTasksHeaderViewHolder(private val binding: CompletedTasksHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindHeader(headerItem: CompletedTasksHeader) {
        // Bind the header data to your CompletedTasksHeaderViewHolder views
        // For example:
        binding.headerDateTextView.text =
            buildString {
                append("Your score for ")
                append(headerItem.date)
                append(" is: ")
                append(headerItem.score)
            }
    }
}
