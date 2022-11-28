package com.example.gawekerjo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityRegisterBinding
import com.example.gawekerjo.model.UserItem
import com.example.gawekerjo.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var b:ActivityRegisterBinding
    private val c=CoroutineScope(Dispatchers.IO)
    private lateinit var db:AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b= ActivityRegisterBinding.inflate(layoutInflater)
        val v=b.root
        setContentView(v)
        db=AppDatabase.Build(this)
        with(b){
            btnRegisterRegister.setOnClickListener {
                val nama=etRegisterName.text.toString()
                val email=etRegisterEmail.text.toString()
                val pass=etRegisterPassword.text.toString()
                val conf=etRegisterConfirmation.text.toString()
                if (nama.isNotEmpty()&&email.isNotEmpty()&&pass.isNotEmpty()&&conf.isNotEmpty()){
                    if (pass==conf){
                        c.launch {
                            val userDao=db.userDao
                            if(userDao.getUserByEmail(email)==null){
                                val userRepository=UserRepository(db)
                                userRepository.Register(email,pass,nama)
                                val user=userDao.getLastUser()
                                runOnUiThread {
                                    etRegisterName.text.clear()
                                    etRegisterConfirmation.text.clear()
                                    etRegisterEmail.text.clear()
                                    etRegisterPassword.text.clear()
                                }
                                val i=Intent(this@RegisterActivity,HomeActivity::class.java)
                                i.putExtra("user",user)
                                startActivity(i)

                            }else{
                                runOnUiThread { Toast.makeText(this@RegisterActivity,"Email ini sudah terpakai",Toast.LENGTH_SHORT).show() }
                            }
                        }
                    }else{
                        runOnUiThread { Toast.makeText(this@RegisterActivity,"Password dan confirmation password tidak sama!",Toast.LENGTH_SHORT).show() }
                    }
                }else{
                    runOnUiThread { Toast.makeText(this@RegisterActivity,"Inputan ada yang kosong!",Toast.LENGTH_SHORT).show() }
                }
            }
            btnRegisterToLogin.setOnClickListener {
                finish()
            }
        }
    }
}