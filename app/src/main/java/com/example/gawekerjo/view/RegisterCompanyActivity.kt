package com.example.gawekerjo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityRegisterCompanyBinding
import com.example.gawekerjo.model.user.User
import com.example.gawekerjo.repository.AccountRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class RegisterCompanyActivity : AppCompatActivity() {

    private lateinit var b: ActivityRegisterCompanyBinding
    private val c= CoroutineScope(Dispatchers.IO)
    private lateinit var db: AppDatabase
    private lateinit var accountRepo : AccountRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b= ActivityRegisterCompanyBinding.inflate(layoutInflater)
        val v=b.root
        setContentView(v)
        db=AppDatabase.Build(this)

        accountRepo = AccountRepository(db)

        b.btnCRegisterRegister.setOnClickListener {
            var name : String = b.etCRegisterName.text.toString()
            var email : String = b.etCRegisterEmail.text.toString()
            var number : String = b.etCRegisterNotelp.text.toString()
            var password : String = b.etCRegisterPassword.text.toString()
            var confirm : String = b.etCRegisterConfirmation.text.toString()

            var error_empty = ""

            if(name == ""){
                error_empty += " name"
            }
            if(email == ""){
                error_empty += " email"
            }
            if(number == ""){
                error_empty += " number"
            }
            if(password == ""){
                error_empty += " password"
            }

            if(error_empty != ""){
                Toast.makeText(this, "Error, field "+error_empty+" cannot be empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(password != confirm){
                Toast.makeText(this, "Error, Password and confirmation password does not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            b.loadModal.visibility = View.VISIBLE

            accountRepo.register(this,0 ,email, password, name, number)
        }

        b.btnCRegisterToUserRegister.setOnClickListener {
            if(b.loadModal.visibility == View.VISIBLE){
                return@setOnClickListener
            }

            val i : Intent = Intent(this, RegisterActivity::class.java)
            startActivity(i)

            this.finish()
        }

        b.btnCRegisterToLogin.setOnClickListener {
            if(b.loadModal.visibility == View.VISIBLE){
                return@setOnClickListener
            }

            val i : Intent = Intent(this, LoginActivity::class.java)
            startActivity(i)

            this.finish()
        }

    }

    fun registerCallback(result : User){
        b.loadModal.visibility = View.INVISIBLE
        if(result.status == 200){
            var i : Intent = Intent(this, HomeActivity::class.java)
            i.putExtra("userlogin",result.data[0])
            startActivity(i)
            this.finish()
        }else{
            Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
        }
    }
}