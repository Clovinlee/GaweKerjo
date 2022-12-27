package com.example.gawekerjo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityMainBinding
import com.example.gawekerjo.view.LoginActivity


class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding
    private lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        db = AppDatabase.Build(this)

//        var userRepo : UserRepository = UserRepository(db)
//        userRepo.loadUserData()


        val i : Intent = Intent(this, LoginActivity::class.java)

//        var x : CountryRepository = CountryRepository(db)
//        x.getAllCountries()

        startActivity(i)
        this.finish()
    }
}