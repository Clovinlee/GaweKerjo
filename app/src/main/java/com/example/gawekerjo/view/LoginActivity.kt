package com.example.gawekerjo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.widget.Toast
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityHomeBinding
import com.example.gawekerjo.databinding.ActivityLoginBinding
import com.example.gawekerjo.model.UserItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigInteger
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {

    private lateinit var b: ActivityLoginBinding
    private val coroutine = CoroutineScope(Dispatchers.IO)
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        b = ActivityLoginBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        db = AppDatabase.Build(this)

        b.btnLoginLogin.setOnClickListener{

            val email : String = b.txtLoginEmail.text.toString()
            var password : String = b.txtLoginPassword.text.toString()
            coroutine.launch {

                var listUser : List<UserItem> = db.userDao.getAllUser()
                for (u : UserItem in listUser){
                    Log.d("CCD",u.email)
                    Log.d("CCD",u.password)
                }

                val usr : UserItem? = db.userDao.getUserByEmailPassword(email, password)
                if(usr == null){
                    runOnUiThread {
                        Toast.makeText(it.context, "User not found", Toast.LENGTH_SHORT).show()
                        b.txtLoginPassword.setText("")
                    }
                }else{
                    val i : Intent = Intent(it.context, HomeActivity::class.java)
                    startActivity(i)
                }
            }
        }

        //TODO : REMEMBER ME
        // OTHER API

        b.btnLoginToRegister.setOnClickListener {
            val i : Intent = Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }
    }

    fun md5(input:String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}