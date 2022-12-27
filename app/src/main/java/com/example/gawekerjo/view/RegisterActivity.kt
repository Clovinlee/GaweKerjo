package com.example.gawekerjo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityRegisterBinding
import com.example.gawekerjo.model.user.User
import com.example.gawekerjo.repository.AccountRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

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

        b.btnRegisterRegister.setOnClickListener {
            val nama=b.etRegisterName.text.toString()
            val email=b.etRegisterEmail.text.toString()
            val pass=b.etRegisterPassword.text.toString()
            val conf=b.etRegisterConfirmation.text.toString()
            if (nama.isNotEmpty()&&email.isNotEmpty()&&pass.isNotEmpty()&&conf.isNotEmpty()){
                if (pass==conf){
                    val accountRepo=AccountRepository(db)
                    accountRepo.registerUser(this, email,pass,nama)
                }else{
                    runOnUiThread { Toast.makeText(this@RegisterActivity,"Password dan confirmation password tidak sama!",Toast.LENGTH_SHORT).show() }
                }
            }else{
                runOnUiThread { Toast.makeText(this@RegisterActivity,"Inputan ada yang kosong!",Toast.LENGTH_SHORT).show() }
            }
        }

        b.btnRegisterToLogin.setOnClickListener {
            val i : Intent = Intent(this, LoginActivity::class.java)
            startActivity(i)

            this.finish()
        }

        b.btnRegisterToCompany.setOnClickListener {
            val i : Intent = Intent(this, RegisterCompanyActivity::class.java)
            startActivity(i)

            this.finish()
        }
    }

    fun registerCallback(result : User){
        b.loadModal.visibility = View.INVISIBLE
        Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
        if(result.status == 200){
            var i : Intent = Intent(this, HomeActivity::class.java)
            i.putExtra("userlogin",result.data[0])
            startActivity(i)
            this.finish()
        }
    }
}