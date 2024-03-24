package com.android.geoquiz

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert.*

import org.junit.Test

class QuizViewModelTest {

    @Test
    fun `calculate score is a whole number`() {
        val savedStateHandle = SavedStateHandle()
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertEquals(70, quizViewModel.calculateScore(7, 10))
    }

    @Test
    fun `provides expected question text`() {
        val savedStateHandle = SavedStateHandle()
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertEquals(R.string.model, quizViewModel.currentQuestionText)
    }

    @Test
    fun `provides expected question answer`() {
        val savedStateHandle = SavedStateHandle()
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertEquals(true, quizViewModel.currentQuestionAnswer)
    }

    @Test
    fun `when move next new question loads`() {
        val savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 13))
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertEquals(R.string.resumed_state, quizViewModel.currentQuestionText)
        // TODO: decouple update question so this works
//        quizViewModel.moveToNext()
//        assertEquals(R.string.onCreate_invoke, quizViewModel.currentQuestionText)
//        quizViewModel.moveToNext()
//        assertEquals(R.string.model, quizViewModel.currentQuestionText)
    }

}