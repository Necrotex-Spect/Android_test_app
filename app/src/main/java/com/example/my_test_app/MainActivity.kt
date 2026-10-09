package com.example.my_test_app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.editText)
        val btnOpenSecond = findViewById<Button>(R.id.btnOpenSecond)
        val btnCall = findViewById<Button>(R.id.btnCall)
        val btnShare = findViewById<Button>(R.id.btnShare)

        // 1. ЯВНЫЙ ИНТЕНТ
        btnOpenSecond.setOnClickListener {
            val text = editText.text.toString().trim()
            if (text.isEmpty()) {
                Toast.makeText(this, "Введите текст!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("EXTRA_TEXT", text)
            startActivity(intent)
        }

        // 2. НЕЯВНЫЙ ИНТЕНТ — звонок
        btnCall.setOnClickListener {
            val phone = editText.text.toString().trim()
            if (phone.isEmpty()) {
                Toast.makeText(this, "Введите номер телефона!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!phone.matches(Regex("^[+]?[0-9\\-() ]{3,20}$"))) {
                Toast.makeText(this, "Некорректный номер телефона!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phone")
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "Приложение для звонков не найдено", Toast.LENGTH_SHORT).show()
            }
        }

        // 3. СИСТЕМНЫЙ ИНТЕНТ — поделиться
        btnShare.setOnClickListener {
            val text = editText.text.toString().trim()
            if (text.isEmpty()) {
                Toast.makeText(this, "Введите текст для отправки!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }
            startActivity(Intent.createChooser(intent, "Поделиться через…"))
        }
    }
}