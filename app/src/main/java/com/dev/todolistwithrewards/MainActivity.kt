package com.dev.todolistwithrewards

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.todolistwithrewards.databinding.ActivityMainBinding

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

        // Set up an observer to watch changes in totalScore
        taskViewModel.totalScore.observe(this) { newScore ->
            // Update the TextView with the new score value
            binding.scoreValueOnMainPage.text = newScore.toString()
        }

        // Set up OnClickListener for the score text or scoreValueOnMainPage
        binding.usePointsButton.setOnClickListener {
            // Handle click on the score text
            NewScoreSheet(null).show(supportFragmentManager, "newScoreTag")
        }

        // Set up an observer to watch changes in totalScore from ScoreViewModel
        scoreViewModel.totalScore.observe(this) { newScore ->
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


    private fun setRecyclerView() {
        val mainActivity = this
        taskViewModel.taskItems.observe(this) {
            binding.todoListRecyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = TaskItemAdapter(it, mainActivity)
            }
        }
    }

    override fun editTaskItem(taskItem: TaskItem) {
        NewTaskSheet(taskItem).show(supportFragmentManager, "newTaskTag")
    }

    override fun completeTaskItem(taskItem: TaskItem) {
        taskViewModel.setCompleted(taskItem)
    }
}