package com.dev.todolistwithrewards

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.todolistwithrewards.databinding.CompletedTasksHeaderBinding

class CompletedTasksHeaderAdapter(
    private val headerItems: List<CompletedTasksHeader>
) : RecyclerView.Adapter<CompletedTasksHeaderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompletedTasksHeaderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CompletedTasksHeaderBinding.inflate(inflater, parent, false)
        return CompletedTasksHeaderViewHolder(binding)
    }

    override fun getItemCount() = headerItems.size

    override fun onBindViewHolder(holder: CompletedTasksHeaderViewHolder, position: Int) {
        holder.bindHeader(headerItems[position])
    }
}
