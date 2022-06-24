package com.android.geoquiz

/*
    Activity is a class within the Android SDK. We need an instance of this class
    in order to enable a UI.

    Subclasses of Activity are needed to implement functionality. A simple app
    like GeoQuiz needs only one activity, MainActivity. This is written in XML
 */

import androidx.appcompat.app.AppCompatActivity // compatibility support for older Androids
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    // inflates a layout and puts it on screen
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}