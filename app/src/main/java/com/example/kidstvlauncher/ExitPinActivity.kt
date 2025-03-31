package com.example.kidstvlauncher

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ExitPinActivity : AppCompatActivity() {

    private lateinit var etPin: EditText
    private lateinit var btnSubmitPin: Button

    // Store the correct PIN
    private val correctPin = "1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exit_pin)

        etPin = findViewById(R.id.etPin)
        btnSubmitPin = findViewById(R.id.btnSubmitPin)

        btnSubmitPin.setOnClickListener {
            validatePin()
        }
    }

    private fun validatePin() {
        val enteredPin = etPin.text.toString()

        if (enteredPin == correctPin) {
            Toast.makeText(this, "Exit Successful", Toast.LENGTH_SHORT).show()
            finishAffinity() // Exit app and return to main launcher
        } else {
            Toast.makeText(this, "Incorrect PIN. Try Again!", Toast.LENGTH_SHORT).show()
            etPin.text.clear()
        }
    }
}
