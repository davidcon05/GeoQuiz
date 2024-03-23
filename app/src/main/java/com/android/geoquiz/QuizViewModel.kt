package com.android.geoquiz

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import java.lang.Exception
import kotlin.math.roundToInt

private const val TAG = "QuizViewModel"
const val CURRENT_INDEX_KEY = "currentIndex"

class QuizViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    var correct = 0
    var currentIndex: Int = savedStateHandle.get<Int>(CURRENT_INDEX_KEY) ?: 0

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
        if(currentIndex > questionBank.size - 1) currentIndex = 0
        // Robust log message to help debug the following line of code
        Log.d(TAG, "moveToNext() called: updating question text", Exception())
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev() {
        if(currentIndex < 0) currentIndex = questionBank.size - 1
        currentIndex = (currentIndex - 1) % questionBank.size
    }

    fun calculateScore(correct: Int, questionBankSize: Int): Int {
        return (correct.toDouble() / questionBankSize * 100.0).roundToInt()
    }
}