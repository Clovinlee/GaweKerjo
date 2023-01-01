package com.example.gawekerjo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityAddBahasaBinding
import com.example.gawekerjo.databinding.ActivityEditProfileUserBinding
import com.example.gawekerjo.databinding.ActivityUserprofileBinding
import com.example.gawekerjo.model.user.UserItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class EditProfileUserActivity : AppCompatActivity() {

    private lateinit var b: ActivityEditProfileUserBinding
    private lateinit var db : AppDatabase
    private val coroutine = CoroutineScope(Dispatchers.IO)
    private lateinit var user : UserItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityEditProfileUserBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        db = AppDatabase.Build(this)

        try {

            user = intent.getParcelableExtra<UserItem>("userLogin")!!
        }catch (e:Exception){
            Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
        }

        loadprofile(user)


        //kembali
        b.imgEditProfileUserBack.setOnClickListener {
            finish()
        }
    }

    fun loadprofile(usr:UserItem){

        b.etEditProfileUserNama.setText("${usr.name}")
        if (usr.description != null){
            b.etEditProfileUserDeskripsi.setText("${usr.description}")
        }
        if (usr.notelp != null){
            b.etEditProfileUserNoTelp.setText("${usr.notelp}")
        }
        if (usr.birthdate != null){
            b.etdEditProfileUserTanggalLahir.setText("${usr.birthdate}")
        }


    }
}