package com.android.geoquiz

/*
    Activity is a class within the Android SDK. We need an instance of this class
    in order to enable a UI.

    Subclasses of Activity are needed to implement functionality. A simple app
    like GeoQuiz needs only one activity, MainActivity. This is written in XML
 */

import androidx.appcompat.app.AppCompatActivity // compatibility support for older Androids
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.android.geoquiz.databinding.ActivityMainBinding
import kotlin.math.roundToInt

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    // declare widgets
    private lateinit var toastMsg: Toast

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

    private var questionsAnswered = BooleanArray(questionBank.size)

    private var currentIndex = 0
    private var correct = 0
    private var response = false
    private var counter = 0


    // inflates a layout and puts it on screen
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)
        toastMsg = Toast.makeText(this, "",Toast.LENGTH_SHORT)

        fun updateQuestion() {
            val questionTextResId = questionBank[currentIndex].textResId
            binding.questionTextView.setText(questionTextResId)
            response = false
        }

        /*
            1) Create turnOnButtons() and put it within the onClickListener for next/prev
            2) Add answered 0/1 to data class? or some sort of array mutable list

         */
        if(!response) {
            binding.trueBtn.setOnClickListener {
                checkAnswer(true)
                turnOffButton(binding.trueBtn, binding.falseBtn)
            }

            binding.falseBtn.setOnClickListener {
                checkAnswer(false)
                turnOffButton(binding.trueBtn, binding.falseBtn)
            }
        }

        binding.prevButton.setOnClickListener {
            currentIndex = (currentIndex - 1) % questionBank.size
            if(currentIndex < 0)
                currentIndex = questionBank.size-1
            updateQuestion()
        }

        binding.nextButton.setOnClickListener {
            currentIndex = ( currentIndex + 1 ) % questionBank.size
            updateQuestion()
            turnOnButton(binding.trueBtn, binding.falseBtn)
        }

        binding.questionTextView.setOnClickListener {
            currentIndex = ( currentIndex + 1 ) % questionBank.size
            updateQuestion()
            turnOnButton(binding.trueBtn, binding.falseBtn)
        }

        updateQuestion()

        setContentView(binding.root)
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

    private fun topToast(stringId: Int) {
        toastMsg.setText(stringId)
        toastMsg.setGravity(Gravity.TOP, 0, 250)
        toastMsg.show()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        var messageResId = R.string.incorrect
        counter++
        if (userAnswer == correctAnswer) {
            messageResId = R.string.correct
            correct++
        }
        response = true
        questionsAnswered[currentIndex] = true
        if(counter == questionBank.size) {
            val score = calculateScore()
            toastMsg.setText("Your score is $score%")
            toastMsg.show()
        } else {
            topToast(messageResId)
        }
    }

    private fun turnOffButton(button1: Button, button2: Button) {
        button1.isEnabled = !(button1.isEnabled)
        button2.isEnabled = !(button2.isEnabled)
        button1.setBackgroundColor(resources.getColor(R.color.white))
        button2.setBackgroundColor(resources.getColor(R.color.white))
    }

    private fun turnOnButton(button1: Button, button2: Button) {
        if (!questionsAnswered[currentIndex]) {
            button1.isEnabled = !(button1.isEnabled)
            button2.isEnabled = !(button2.isEnabled)
            button1.setBackgroundColor(resources.getColor(R.color.buttons))
            button2.setBackgroundColor(resources.getColor(R.color.buttons))
        }
    }

    private fun calculateScore(): Int {
        val correctAnswers:Double = correct.toDouble()
        val questionBankSize:Double = questionBank.size.toDouble()
        return (correctAnswers / questionBankSize * 100.0).roundToInt()
    }

}
