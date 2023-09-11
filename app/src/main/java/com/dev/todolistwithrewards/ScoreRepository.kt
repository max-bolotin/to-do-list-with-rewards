package com.dev.todolistwithrewards

object ScoreRepository {
    private var totalScore: Int = 0

    fun getTotalScore(): Int {
        return totalScore
    }

    fun addToTotalScore(pointsToAdd: Int) {
        totalScore += pointsToAdd
    }

    fun subtractFromTotalScore(pointsToSubtract: Int) {
        totalScore -= pointsToSubtract
    }
}
