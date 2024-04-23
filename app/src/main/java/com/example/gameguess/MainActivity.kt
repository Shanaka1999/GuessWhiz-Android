package com.example.gameguess

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val startGameButton = findViewById<Button>(R.id.startButton)
        startGameButton.setOnClickListener {
            val intent = Intent(this, GuessingActivity::class.java)
            startActivity(intent)
        }
    }

}