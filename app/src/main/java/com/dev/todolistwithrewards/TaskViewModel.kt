package com.dev.todolistwithrewards

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

class TaskViewModel : ViewModel() {
    var taskItems = MutableLiveData<MutableList<TaskItem>>()

    init {
        taskItems.value = mutableListOf()
    }

    fun addTaskItem(newTask: TaskItem) {
        val list = taskItems.value
        list!!.add(newTask)
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
        }
        taskItems.postValue(list)
    }
}