// ResultActivity.kt
// GuessingActivity.kt
package com.example.gameguess

import DatabaseHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result_layout)

        val resultTextView: TextView = findViewById(R.id.result_text_view)
        val resultMessage = intent.getStringExtra("result_message")
        resultTextView.text = resultMessage

        val dbHelper = DatabaseHelper(this)
        val highestScore = dbHelper.getHighestScore()
        val highestScoreTextView: TextView = findViewById(R.id.highest_score_text_view)
        highestScoreTextView.text = "Highest Score: $highestScore"

        val replayButton: Button = findViewById(R.id.replay_button)
        replayButton.setOnClickListener {
            val intent = Intent(this, GuessingActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

