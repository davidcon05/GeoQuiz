package com.android.geoquiz

/*
    Activity is a class within the Android SDK. We need an instance of this class
    in order to enable a UI.

    Subclasses of Activity are needed to implement functionality. A simple app
    like GeoQuiz needs only one activity, MainActivity. This is written in XML
 */

import android.media.Image
import androidx.appcompat.app.AppCompatActivity // compatibility support for older Androids
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    // declare widgets
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
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

    private var currentIndex = 0

    // inflates a layout and puts it on screen
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.trueBtn)
        falseButton = findViewById(R.id.falseBtn)
        nextButton = findViewById(R.id.nextButton)
        prevButton = findViewById(R.id.prevButton)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener { _: View ->
            checkAnswer(true)
        }

        falseButton.setOnClickListener { _: View ->
            checkAnswer(false)
        }

        prevButton.setOnClickListener {
            currentIndex = (currentIndex - 1) % questionBank.size
            if(currentIndex < 0)
                currentIndex = questionBank.size-1
            updateQuestion()
        }

        nextButton.setOnClickListener {
            currentIndex = ( currentIndex + 1 ) % questionBank.size
            updateQuestion()
        }

        questionTextView.setOnClickListener {
            currentIndex = ( currentIndex + 1 ) % questionBank.size
            updateQuestion()
        }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun topToast(stringId: Int, timeout: Int) {
        val toast = Toast.makeText(this, stringId, timeout)
        toast.setGravity(Gravity.TOP, 0, 250)
        toast.show()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct
        } else {
            R.string.incorrect
        }

        topToast(messageResId, Toast.LENGTH_SHORT)
    }
}
