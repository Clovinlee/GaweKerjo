package com.example.gawekerjo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityLoginBinding
import com.example.gawekerjo.model.company.Company
import com.example.gawekerjo.model.user.User
import com.example.gawekerjo.repository.AccountRepository
import kotlinx.coroutines.*
import java.math.BigInteger
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {

    private lateinit var b: ActivityLoginBinding
    private val coroutine = CoroutineScope(Dispatchers.IO)
    private lateinit var db: AppDatabase
    private lateinit var accRepo : AccountRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        b = ActivityLoginBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        b.loadModal.visibility = View.GONE

        db = AppDatabase.Build(this)

        accRepo = AccountRepository(db)

        b.btnLoginLogin.setOnClickListener{
            val email : String = b.txtLoginEmail.text.toString()
            var password : String = b.txtLoginPassword.text.toString()

            // LOAD SCREEN
             b.loadModal.visibility = View.VISIBLE

            accRepo.loginUser(this,email ,password)
        }

        b.btnLoginToRegister.setOnClickListener {
            val i : Intent = Intent(this, RegisterActivity::class.java)
            startActivity(i)

            this.finish()
        }

        //TODO : REMEMBER ME
        // OTHER API
    }

    fun verifyLoginUser(result : User){
        if(result.data.size > 0){
            val usr = result.data[0]
            b.loadModal.visibility = View.INVISIBLE
            val i : Intent = Intent(this, HomeActivity::class.java)
            i.putExtra("userlogin", usr)
            startActivity(i)
        }else{
            accRepo.loginCompany(this,b.txtLoginEmail.text.toString(), b.txtLoginPassword.text.toString())
        }
    }

    fun verifyLoginCompany(result : Company){
        if(result.data.size > 0){
            val cmp = result.data[0]
            b.loadModal.visibility = View.INVISIBLE
            val i : Intent = Intent(this, HomeActivity::class.java)
            i.putExtra("userlogin", cmp)
            startActivity(i)
        }else{
            b.loadModal.visibility = View.INVISIBLE
            runOnUiThread {
                Toast.makeText(this, "Account not found!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun md5(input:String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}