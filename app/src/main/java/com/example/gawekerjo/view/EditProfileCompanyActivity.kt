package com.example.gawekerjo.view

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityEditProfileCompanyBinding
import com.example.gawekerjo.databinding.ActivityEditProfileUserBinding
import com.example.gawekerjo.env
import com.example.gawekerjo.model.user.User
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.repository.CountryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class EditProfileCompanyActivity : AppCompatActivity() {

    private lateinit var b: ActivityEditProfileCompanyBinding
    private lateinit var db: AppDatabase
    private val coroutine = CoroutineScope(Dispatchers.IO)
    private lateinit var us: UserItem
    private lateinit var user: UserItem
    private var urlgamber:Uri?=null

    val REQUEST_CODE = 100

    private lateinit var countryRepo : CountryRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityEditProfileCompanyBinding.inflate(layoutInflater)
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
            loadprofilec(user)
        }



    }


    fun loadprofilec(usr:UserItem){

        b.etEditProfileCompanyNama.setText("${usr.name}")

        if (usr.description != null){
            b.etEditProfileCompanyDeskripsi.setText("${usr.description}")
        }
        if (usr.lokasi != null){

            b.autoCompleteTextView.setText("${usr.lokasi}")
        }
        if (usr.founded != null){

            b.etEditProfileCompanyFounder.setText("${usr.founded}")
        }
        if (usr.industry != null){

            b.etEditProfileCompanyIndustry.setText("${usr.industry}")
        }
        if (usr.notelp != null){
            b.etEditProfileCompanyNoTelp.setText("${usr.notelp}")
        }
//        if(usr.image!=null){
//            //runOnUiThread { Toast.makeText(this, "ambil gambar", Toast.LENGTH_SHORT).show() }
//            val i= URL(env.API_URL.substringBefore("/api/")+usr.image).openStream()
//            val image= BitmapFactory.decodeStream(i)
//            runOnUiThread { b.Edit.setImageBitmap(image) }
//        }
    }

    fun balek (result: User){
        if (result.status == 200){
            var i = Intent(this, CompanyProfileActivity::class.java)
            i.putExtra("userLogin", result.data[0])
            startActivity(i)
            this.finish()
        }else{
            Toast.makeText(this, "${result.message}", Toast.LENGTH_SHORT).show()
        }
    }
}