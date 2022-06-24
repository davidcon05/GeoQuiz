package com.android.geoquiz

/*
    Activity is a class within the Android SDK. We need an instance of this class
    in order to enable a UI.

    Subclasses of Activity are needed to implement functionality. A simple app
    like GeoQuiz needs only one activity, MainActivity. This is written in XML
 */

import androidx.appcompat.app.AppCompatActivity // compatibility support for older Androids
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    // declare widgets
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button

    fun topToast(stringId: Int, timeout: Int) {
        val toast = Toast.makeText(this, stringId, timeout)
        toast.setGravity(Gravity.TOP, 0, 250)
        toast.show()
    }

    // inflates a layout and puts it on screen
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.trueBtn)
        falseButton = findViewById(R.id.falseBtn)

        trueButton.setOnClickListener { view: View ->
            topToast(R.string.correct, Toast.LENGTH_SHORT)

        }

        falseButton.setOnClickListener { view: View ->
            topToast(R.string.incorrect, Toast.LENGTH_SHORT)
        }


    }
}
