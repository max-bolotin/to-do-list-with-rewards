package com.dev.todolistwithrewards

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.time.LocalDate

class TaskViewModel(private val repository: TaskItemRepository) : ViewModel() {
    var taskItems: MutableLiveData<List<TaskItem>> =
        repository.allTaskItems.asLiveData() as MutableLiveData<List<TaskItem>>
    var totalScore = MutableLiveData<Int>()

    // Add a MutableLiveData for uncompleted tasks
    lateinit var uncompletedTasks: MutableList<TaskItem>

    init {
        totalScore.value = 0
    }

    fun addTaskItem(newTask: TaskItem) = viewModelScope.launch {
        repository.insertTaskItem(newTask)
    }

    fun setCompleted(taskItem: TaskItem) = viewModelScope.launch {

        // Task already exists, update its completion status
        if (taskItem.completedDate() == null) {
            taskItem.completedDateString = TaskItem.dateFormatter.format(LocalDate.now())
            totalScore.value = totalScore.value?.plus(taskItem.score ?: 0)
            ScoreRepository.addToTotalScore(taskItem.score ?: 0)
        }

        // Separate completed and uncompleted tasks
        val list = taskItems.value.orEmpty()
        val completedTasks = list.filter { it.completedDateString != null }
        val updatedCompletedTasks = completedTasks.toMutableList()
        if (!updatedCompletedTasks.contains(taskItem)) {
            updatedCompletedTasks.add(taskItem)
        }

        uncompletedTasks = list.filter { it.completedDateString == null }.toMutableList()

        println("uncompletedTasks = ${uncompletedTasks}")
        println("updatedCompletedTasks = ${updatedCompletedTasks}")

        // Combine uncompleted tasks and sorted completed tasks
        val updatedList = uncompletedTasks + updatedCompletedTasks
        println("updatedList = ${updatedList}")

        taskItems.postValue(updatedList)
        totalScore.postValue(totalScore.value)
        repository.updateTaskItem(taskItem)
    }

    fun updateTaskItem(taskItem: TaskItem) = viewModelScope.launch {
        repository.updateTaskItem(taskItem)
    }
}

class TaskItemModelFactory(private val repository: TaskItemRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java))
            return TaskViewModel(repository) as T

        throw IllegalArgumentException("Unknown class for View Model")
    }
}

/*fun addTaskItem(newTask: TaskItem) {
    val list = taskItems.value
    list!!.add(0, newTask)
    taskItems.postValue(list)
}*/

/*    fun updateTaskItem(
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
    }*/

/*fun setCompleted(taskItem: TaskItem) {
    val list = taskItems.value
    val task = list!!.find { it.id == taskItem.id }!!
    if (task.completedDate == null) {
        task.completedDate = LocalDate.now()
//            totalScore.value = totalScore.value?.plus(task.score ?: 0)
        ScoreRepository.addToTotalScore(task.score ?: 0)
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
}*/
