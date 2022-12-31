package com.example.gawekerjo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityAddBahasaBinding

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class AddBahasaActivity : AppCompatActivity() {

    private lateinit var b: ActivityAddBahasaBinding
    private lateinit var db : AppDatabase
    private val coroutine = CoroutineScope(Dispatchers.IO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        b = ActivityAddBahasaBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        db = AppDatabase.Build(this)
//        setContentView(R.layout.activity_add_bahasa)


        // kembali
        b.imgTambahBahasaBack.setOnClickListener {
            finish()
        }


    }

    override fun onResume() {
        super.onResume()
        val kemahiran = resources.getStringArray(R.array.kemahiran)
        val arrayAdapter = ArrayAdapter(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, kemahiran)
        b.autoCompleteTextView.setAdapter(arrayAdapter)
    }
}