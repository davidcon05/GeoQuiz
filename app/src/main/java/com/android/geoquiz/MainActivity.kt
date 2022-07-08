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
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.android.geoquiz.databinding.ActivityMainBinding
import kotlin.math.roundToInt

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {
    // declare widgets
    private lateinit var toastMsg: Toast

    // lazy allows for making qVM a val instead of a var
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    private var correct = 0

    // inflates a layout and puts it on screen
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        toastMsg = Toast.makeText(this, "",Toast.LENGTH_SHORT)

        fun updateQuestion() {
            val questionTextResId = quizViewModel.currentQuestionText
            binding.questionTextView.setText(questionTextResId)
            if(quizViewModel.questionsAnswered[quizViewModel.currentIndex]) {
                turnOffButton(binding.trueBtn, binding.falseBtn)
            }
        }

        if(quizViewModel.questionsAnswered[quizViewModel.currentIndex]) {
            turnOffButton(binding.trueBtn, binding.falseBtn)
        }

        binding.trueBtn.setOnClickListener {
            checkAnswer(true, binding.trueBtn)
            turnOffButton(binding.trueBtn, binding.falseBtn)
        }

        binding.falseBtn.setOnClickListener {
            checkAnswer(false, binding.falseBtn)
            turnOffButton(binding.trueBtn, binding.falseBtn)
        }

        binding.prevButton.setOnClickListener {
            quizViewModel.moveToPrev()
            updateQuestion()
            turnOnButton(binding.trueBtn, binding.falseBtn)
            quizViewModel.currentIndex--
        }

        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
            turnOnButton(binding.trueBtn, binding.falseBtn)
        }

        binding.questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
            turnOnButton(binding.trueBtn, binding.falseBtn)
        }

        updateQuestion()
        setContentView(binding.root)
    }



    private fun topToast(stringId: Int) {
        toastMsg.setText(stringId)
        toastMsg.setGravity(Gravity.TOP, 0, 250)
        toastMsg.show()
    }

    private fun checkAnswer(userAnswer: Boolean, button: Button) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        var messageResId = R.string.incorrect

        if (userAnswer == correctAnswer) {
            messageResId = R.string.correct
            button.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.green))
            correct++
        } else {
            button.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.red))
        }

        quizViewModel.questionsAnswered.set(quizViewModel.currentIndex, true)

        if(quizViewModel.currentIndex == quizViewModel.questionBank.size) {
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
        quizViewModel.currentIndex++
    }

    private fun turnOnButton(button1: Button, button2: Button) {
        if (!quizViewModel.questionsAnswered[quizViewModel.currentIndex]) {
            button1.isEnabled = !(button1.isEnabled)
            button2.isEnabled = !(button2.isEnabled)
            button1.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.buttons))
            button2.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.buttons))
        }
    }

    private fun calculateScore(): Int {
        val correctAnswers:Double = correct.toDouble()
        val questionBankSize:Double = quizViewModel.questionBank.size.toDouble()
        return (correctAnswers / questionBankSize * 100.0).roundToInt()
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

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveSInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }
}
