package com.example.buddyapp.Screens

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.example.buddyapp.R

class SplashScreen : AppCompatActivity() {
    companion object {
        //CODIND FOR POST DELAY
        private val SPLASH_TIME_OUT: Long = 3000 // 3 seconds (adjust as needed)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        // Use a Handler to delay the transition to the main activity
        Handler().postDelayed({
            // Create an intent to start the main activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            // Finish this activity to prevent going back to the splash screen
            finish()
        }, SPLASH_TIME_OUT)}}
