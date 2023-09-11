package com.dev.todolistwithrewards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dev.todolistwithrewards.databinding.FragmentScoreBinding

class ScoreFragment : Fragment() {

    private var _binding: FragmentScoreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialize view binding
        _binding = FragmentScoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the totalScore from arguments (you should pass it when navigating to this fragment)
        val totalScore = arguments?.getInt("totalScore", 0)

        // Display the total score in the TextView using view binding
        binding.scoreValue.text = totalScore.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // Clean up view binding
    }
}
