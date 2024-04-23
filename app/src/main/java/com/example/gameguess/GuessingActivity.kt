// GuessingActivity.kt
package com.example.gameguess

import DatabaseHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlin.random.Random

class GuessingActivity : AppCompatActivity() {

    private lateinit var userInput: EditText
    private lateinit var submitButton: Button
    private lateinit var hintTextView: TextView
    private lateinit var scoreTextView: TextView

    private var randomNumber = 0
    private var remainingGuesses = 10
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.guessing_layout)

        userInput = findViewById(R.id.user_input)
        submitButton = findViewById(R.id.submit_button)
        hintTextView = findViewById(R.id.hint_text_view)
        scoreTextView = findViewById(R.id.score_text_view)
        submitButton.setOnClickListener { checkGuess() }

        startNewGame()
    }

    private fun startNewGame() {
        randomNumber = Random.nextInt(1, 101)
        println(randomNumber)
        remainingGuesses = 10
        score = 0
        updateScore()
        updateHint("Guess a number between 1 and 100.")
    }

    private fun checkGuess() {
        val userInputString = userInput.text.toString()
        val userInputValue = userInputString.toIntOrNull()

        userInput.text.clear()

        if (userInputValue != null) {
            remainingGuesses--

            if (userInputValue == randomNumber) {
                updateHint("Congratulations! You guessed the correct number.")
                val score = (1+remainingGuesses) * 10 // Calculate score before displaying
                val dbHelper = DatabaseHelper(this)
                dbHelper.addScore(score)
                val resultIntent = Intent(this, ResultActivity::class.java)
                resultIntent.putExtra("result_message", "You guessed the correct number! Your score: $score")
                startActivity(resultIntent)
                finish() // Finish this activity to prevent going back to it from the result screen
            } else {
                if (remainingGuesses > 0) {
                    if (userInputValue < randomNumber) {
                        updateHint("Too low. Guess higher.", isLow = true)
                    } else {
                        updateHint("Too high. Guess lower.", isHigh = true)
                    }

                } else {
                    updateHint("Out of guesses. The correct number was $randomNumber")
                    val resultIntent = Intent(this, ResultActivity::class.java)
                    resultIntent.putExtra("result_message", "Out of guesses. The correct number was $randomNumber.")
                    startActivity(resultIntent)
                    finish() // Finish this activity to prevent going back to it from the result screen
                }
            }
            updateScore() // Update remaining attempts after each guess
        } else {
            updateHint("Please enter a valid number.")
        }
    }


    private fun updateHint(message: String, isHigh: Boolean = false, isLow: Boolean = false) {
        hintTextView.text = message
        if (isHigh) {
            hintTextView.setTextColor(ContextCompat.getColor(this, R.color.colorHigh)) // Assuming colorHigh is defined in your resources
        } else if (isLow) {
            hintTextView.setTextColor(ContextCompat.getColor(this, R.color.colorLow)) // Assuming colorLow is defined in your resources
        } else {
            hintTextView.setTextColor(ContextCompat.getColor(this, R.color.colorDefault)) // Assuming colorDefault is defined in your resources
        }
    }

    private fun updateScore() {
        scoreTextView.text = "Remaining Attempts: $remainingGuesses"
    }


}
