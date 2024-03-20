package com.android.geoquiz

import androidx.lifecycle.ViewModel
import kotlin.math.roundToInt

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {

    var currentIndex = 0
    var correct = 0

    val questionBank = listOf(
        Question(R.string.model, true),
        Question(R.string.model_to_controller, true),
        Question(R.string.views, true),
        Question(R.string.view_code, false),
        Question(R.string.view_controller, false),
        Question(R.string.controller_to_model, true),
        Question(R.string.controller_to_view, true),
        Question(R.string.mvc_weakness, false),
        Question(R.string.activity, true),
        Question(R.string.view_examples, false),
        Question(R.string.nonexistent_state, true),
        Question(R.string.stopped_state, false),
        Question(R.string.paused_state, true),
        Question(R.string.resumed_state, true),
        Question(R.string.onCreate_invoke, false)
    )

    var questionsAnswered = BooleanArray(questionBank.size)

    val currentQuestionAnswer: Boolean get() = questionBank[currentIndex].answer
    val currentQuestionText: Int get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
        currentIndex++
        if(currentIndex == questionBank.size)
            currentIndex = 0
    }

    fun moveToPrev() {
        currentIndex = (currentIndex - 1) % questionBank.size
        if(currentIndex < 0)
            currentIndex = questionBank.size - 1
    }

    fun calculateScore(): Int {
        val correctAnswers:Double = correct.toDouble()
        val questionBankSize:Double = questionBank.size.toDouble()
        return (correctAnswers / questionBankSize * 100.0).roundToInt()
    }
}