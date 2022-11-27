package com.example.gawekerjo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gawekerjo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)
    }
}