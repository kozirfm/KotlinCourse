package ru.geekbrains.kozirfm.kotlincourse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val hello = "Hello"
        val char = '!'
        val text = "$hello ${getWorld()} $char"

        this.button.setOnClickListener { this.textView.text = text }
    }

    private fun getWorld() = "World"

}
