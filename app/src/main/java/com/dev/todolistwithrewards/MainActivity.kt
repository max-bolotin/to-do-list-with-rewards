package com.dev.todolistwithrewards

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.todolistwithrewards.databinding.ActivityMainBinding
import java.time.LocalDate

class MainActivity : AppCompatActivity(), TaskItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var scoreViewModel: ScoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        // Initialize the scoreViewModel using ViewModelProvider
        scoreViewModel = ViewModelProvider(this).get(ScoreViewModel::class.java)

        binding.newTaskButton.setOnClickListener {
            NewTaskSheet(null).show(supportFragmentManager, "newTaskTag")
        }

        setRecyclerView()


        // Set up OnClickListener for the score text or scoreValueOnMainPage
        binding.usePointsButton.setOnClickListener {
            // Handle click on the score text
            NewScoreSheet(null).show(supportFragmentManager, "newScoreTag")
        }

// Set up an observer to watch changes in totalScore from ScoreRepository
        ScoreRepository.getTotalScoreLiveData().observe(this) { newScore ->
            // Update the TextView with the new score value
            binding.scoreValueOnMainPage.text = newScore.toString()
        }
    }

    private fun openScorePage() {
        // Create a new instance of ScoreFragment and pass the totalScore as an argument
        val scoreFragment = ScoreFragment()
        val bundle = Bundle()
        bundle.putInt("totalScore", taskViewModel.totalScore.value ?: 0)
        scoreFragment.arguments = bundle

        // Replace any existing fragment in the fragment_score container with the ScoreFragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_score, scoreFragment) // Use your fragment container ID
        transaction.addToBackStack(null) // Optional, to allow back navigation
        transaction.commit()
    }

    fun calculateScoreForDate(date: LocalDate): Int {
        val tasksForDate = taskViewModel.taskItems.value!!.filter { it.completedDate == date }
        return tasksForDate.sumOf { it.score!! }
    }

    fun updateScoreForCurrentDate() {
        val currentDate = LocalDate.now()
        val scoreForDate = calculateScoreForDate(currentDate)
        binding.scoreValueOnMainPage.text = scoreForDate.toString()
    }

    private fun setRecyclerView() {
        val mainActivity = this

        taskViewModel.taskItems.observe(this) { taskItems ->
            val groupedTasks = taskItems.groupBy { it.completedDate }

            val itemsWithHeaders = mutableListOf<Any>()

            groupedTasks.forEach { (date, tasks) ->
                val scoreForDate = calculateScoreForDate(date ?: LocalDate.now()) // Use LocalDate.now() or provide a default date
                itemsWithHeaders.add(CompletedTasksHeader(date ?: LocalDate.now(), scoreForDate))
                itemsWithHeaders.addAll(tasks)
            }

            val adapter = TaskItemAdapter(itemsWithHeaders, mainActivity)
            binding.todoListRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
            binding.todoListRecyclerView.adapter = adapter
        }
    }



    override fun editTaskItem(taskItem: TaskItem) {
        NewTaskSheet(taskItem).show(supportFragmentManager, "newTaskTag")
    }

    override fun completeTaskItem(taskItem: TaskItem) {
        taskViewModel.setCompleted(taskItem)
    }
}