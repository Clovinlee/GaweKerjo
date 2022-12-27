package com.example.gawekerjo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gawekerjo.R
import com.example.gawekerjo.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    lateinit var b:ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b=ActivityChatBinding.inflate(layoutInflater)
        val v=b.root
        setContentView(v)

    }
}