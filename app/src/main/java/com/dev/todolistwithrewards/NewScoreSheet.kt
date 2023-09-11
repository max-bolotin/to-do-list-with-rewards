package com.dev.todolistwithrewards

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
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

    @SuppressLint("InflateParams")
    private fun saveAction() {
        val scoreText = binding.score.text.toString()
        val pointsToSubtract = scoreText.toIntOrNull() ?: 0

        val subtractionSuccessful = ScoreRepository.subtractFromTotalScore(pointsToSubtract)

        if (subtractionSuccessful) {
            // Successfully subtracted points, you can dismiss the dialog
            dismiss()
        } else {
            val inflater = LayoutInflater.from(context)
            val layout = inflater.inflate(R.layout.toast_layout, null)

// Find and customize the TextView in the inflated layout
            val textViewToast = layout.findViewById<TextView>(R.id.textViewToast)
            textViewToast.setText(R.string.not_enough_points) // Use the string resource

// Create a Toast with custom view
            val toast = Toast.makeText(context, "", Toast.LENGTH_SHORT)
            toast.view = layout

// Set the gravity for centering the toast
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)

// Show the Toast
            toast.show()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewScoreSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

}

