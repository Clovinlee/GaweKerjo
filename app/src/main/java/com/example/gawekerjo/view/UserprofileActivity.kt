package com.example.gawekerjo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityMainBinding
import com.example.gawekerjo.databinding.ActivityUserprofileBinding
import com.example.gawekerjo.model.user.UserItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserprofileActivity : AppCompatActivity() {

    private lateinit var b: ActivityUserprofileBinding
    private lateinit var db : AppDatabase
    private val coroutine = CoroutineScope(Dispatchers.IO)
    lateinit var user : UserItem
    lateinit var us : UserItem

    override fun onResume() {
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        b = ActivityUserprofileBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        db = AppDatabase.Build(this)
        try {

            us = intent.getParcelableExtra<UserItem>("userLogin")!!
        }catch (e:Exception){
            Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
        }

        coroutine.launch {
            user = db.userDao.getUserByEmail(us.email)!!
            loadprofile(user)
        }


//        Toast.makeText(this, "${user.name}", Toast.LENGTH_SHORT).show()

        //masuk edit profile

        b.imgUserProfileEditProfile.setOnClickListener {
            val i : Intent = Intent(this, EditProfileUserActivity::class.java)
            i.putExtra("userLogin",user)
            startActivity(i)
        }


        // masuk tambah pendidikan
        b.imgUserProfileTambahPendidikan.setOnClickListener {

            val i : Intent = Intent(this, AddPendidikanActivity::class.java)
            startActivity(i)



        }
        // masuk tambah keahliam
        b.imgUserProfileTambahKeahlian.setOnClickListener {
            val i : Intent = Intent(this, AddKeahlianActivity::class.java)
            startActivity(i)
        }

        // masuk tambah bahasa
        b.imgUserProfileTambahBahasa.setOnClickListener {
            val i : Intent = Intent(this, AddBahasaActivity::class.java)
            startActivity(i)
        }



    }

    fun loadprofile(usr:UserItem){

        b.tvUserProfileNama.text = "${usr.name}"
        if (usr.gender == null){
            b.tvUserProfileGender.text = "Belum diatur"
        }else{
            if (usr.gender == "P"){
                b.tvUserProfileGender.text = "Wanita"
            }
            else{
                b.tvUserProfileGender.text = "Pria"
            }
        }
        if (usr.description == null){
            b.tvUserProfileDeskripsi.text = "Kosong"
        }
        else{
            b.tvUserProfileDeskripsi.text = "${usr.description}"
        }

    }



}