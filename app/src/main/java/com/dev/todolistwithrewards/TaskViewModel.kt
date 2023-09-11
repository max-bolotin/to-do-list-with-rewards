package com.dev.todolistwithrewards

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

class TaskViewModel : ViewModel() {
    var taskItems = MutableLiveData<MutableList<TaskItem>>()
    var totalScore = MutableLiveData<Int>()

    init {
        taskItems.value = mutableListOf()
        totalScore.value = 0
    }

    fun addTaskItem(newTask: TaskItem) {
        val list = taskItems.value
        list!!.add(0, newTask)
        taskItems.postValue(list)
    }

    fun updateTaskItem(
        id: UUID,
        name: String,
        description: String,
        dueTime: LocalTime?,
        score: Int?
    ) {
        val list = taskItems.value
        val task = list!!.find { it.id == id }!!
        task.name = name
        task.description = description
        task.dueTime = dueTime
        if (score != null) {
            task.score = score
        }
        taskItems.postValue(list)
    }

    fun setCompleted(taskItem: TaskItem) {
        val list = taskItems.value
        val task = list!!.find { it.id == taskItem.id }!!
        if (task.completedDate == null) {
            task.completedDate = LocalDate.now()
            totalScore.value = totalScore.value?.plus(task.score ?: 0)
        }
        // Separate completed and uncompleted tasks
        val completedTasks = list.filter { it.completedDate != null }
        val uncompletedTasks = list.filter { it.completedDate == null }

        // Sort completed tasks by completion date in descending order
        val sortedCompletedTasks = completedTasks.sortedByDescending { it.completedDate }

        // Combine uncompleted tasks and sorted completed tasks
        val updatedList = uncompletedTasks + sortedCompletedTasks

        taskItems.postValue(updatedList.toMutableList())
        totalScore.postValue(totalScore.value)
    }
}