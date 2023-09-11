package com.dev.todolistwithrewards

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel : ViewModel() {
    var totalScore = MutableLiveData<Int>()

    init {
        totalScore.value = 0
    }

    // Function to subtract points from the totalScore
    fun subtractPoints(points: Int) {
//        totalScore.value = totalScore.value?.minus(points)
        ScoreRepository.subtractFromTotalScore(points)
    }
}