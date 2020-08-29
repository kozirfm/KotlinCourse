package ru.geekbrains.kozirfm.kotlincourse

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)
        val button = findViewById<Button>(R.id.button)

        val hello = "Hello"
        val char = '!'
        val text = "$hello ${getWorld()} $char"

        button.setOnClickListener { textView.text = text }
    }

    private fun getWorld() = "World"

}
