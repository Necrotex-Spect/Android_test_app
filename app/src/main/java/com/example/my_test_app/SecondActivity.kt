package com.example.my_test_app

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val tvReceivedText = findViewById<TextView>(R.id.tvReceivedText)
        val receivedText = intent.getStringExtra("EXTRA_TEXT")

        if (receivedText.isNullOrEmpty()) {
            tvReceivedText.text = "Текст не был передан"
        } else {
            tvReceivedText.text = receivedText
        }
    }
}