package com.dev.todolistwithrewards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object ScoreRepository {
    private var totalScore: Int = 0

    // Create a LiveData property to observe changes in totalScore
    private val totalScoreLiveData = MutableLiveData<Int>()

    init {
        // Initialize the initial score value to 0 when the object is created
        totalScoreLiveData.value = totalScore
    }

    fun getTotalScore(): Int {
        return totalScore
    }

    fun addToTotalScore(pointsToAdd: Int) {
        totalScore += pointsToAdd
        totalScoreLiveData.value = totalScore // Notify observers of the change
    }

    fun subtractFromTotalScore(pointsToSubtract: Int): Boolean {
        if (totalScore >= pointsToSubtract) {
            totalScore -= pointsToSubtract
            totalScoreLiveData.value = totalScore
            return true // Subtraction successful
        }
        return false // Not enough points to subtract
    }

    // Expose the LiveData for observing
    fun getTotalScoreLiveData(): LiveData<Int> {
        return totalScoreLiveData
    }
}

