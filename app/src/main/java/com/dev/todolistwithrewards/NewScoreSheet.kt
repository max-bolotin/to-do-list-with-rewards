package com.dev.todolistwithrewards

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dev.todolistwithrewards.databinding.FragmentNewScoreSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NewScoreSheet(var scoreItem: ScoreItem?) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNewScoreSheetBinding
    private lateinit var scoreViewModel: ScoreViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        if (scoreItem != null) {
            binding.taskTitle.text = "Use your points"
            val editable = Editable.Factory.getInstance()
            binding.score.text = editable.newEditable(scoreItem!!.totalScore.toString())

        } else {
            binding.taskTitle.text = "New points"
        }

        scoreViewModel = ViewModelProvider(activity).get(ScoreViewModel::class.java)
        binding.saveButton.setOnClickListener {
            saveAction()
        }
    }

    private fun saveAction() {
        val scoreText = binding.score.text.toString()

        // Convert the scoreText to an integer (you should handle possible exceptions here)
        val pointsToSubtract = scoreText.toInt()

        // Get a reference to the ScoreViewModel
        val scoreViewModel = ViewModelProvider(requireActivity()).get(ScoreViewModel::class.java)

        // Subtract the points from the totalScore
        scoreViewModel.subtractPoints(pointsToSubtract)

        dismiss()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewScoreSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

}

