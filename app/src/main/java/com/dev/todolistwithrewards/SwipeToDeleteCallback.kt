package com.dev.todolistwithrewards

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView


class SwipeToDeleteCallback(private val adapter: TaskItemAdapter) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        // Don't need to handle drag-and-drop
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition

        // Check if the position is valid and within the bounds of the list
        if (position != RecyclerView.NO_POSITION && position < adapter.itemCount) {
            // Get the task item at the specified position
            val taskItem = adapter.getTaskItem(position)

            // Check if the task is completed before allowing deletion
            if (taskItem!!.isCompleted()) {
                adapter.deleteTask(position)
            } else {
                // Notify the adapter to refresh the view to show the item was not deleted
                adapter.notifyItemChanged(position)
            }
        }
    }

}

