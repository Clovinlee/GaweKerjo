package com.example.gawekerjo.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityHomeBinding
import com.example.gawekerjo.databinding.ActivityLoginBinding
import com.example.gawekerjo.model.UserItem
import com.example.gawekerjo.repository.UserRepository
import kotlinx.coroutines.*
import java.math.BigInteger
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {

    private lateinit var b: ActivityLoginBinding
    private val coroutine = CoroutineScope(Dispatchers.IO)
    private lateinit var db: AppDatabase
    private lateinit var userRepo : UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        b = ActivityLoginBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        b.loadModal.visibility = View.GONE

        db = AppDatabase.Build(this)

        userRepo = UserRepository(db)

        b.btnLoginLogin.setOnClickListener{
            val email : String = b.txtLoginEmail.text.toString()
            var password : String = b.txtLoginPassword.text.toString()

            // LOAD SCREEN
             b.loadModal.visibility = View.VISIBLE

            userRepo.login(this,email ,password)
        }

        //TODO : REMEMBER ME
        // OTHER API

        b.btnLoginToRegister.setOnClickListener {
            val i : Intent = Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }
    }

    fun verifyLogin(context : Context, usr : UserItem?){
        b.loadModal.visibility = View.INVISIBLE
        if(usr != null){
            val i : Intent = Intent(context, HomeActivity::class.java)
            i.putExtra("userlogin",usr)
            startActivity(i)
        }else{
            this.runOnUiThread {
                Toast.makeText(context, "Error, wrong email / password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun md5(input:String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}