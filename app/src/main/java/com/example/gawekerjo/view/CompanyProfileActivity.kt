package com.example.gawekerjo.view

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityCompanyProfileBinding
import com.example.gawekerjo.env
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.repository.AccountRepository
import com.example.gawekerjo.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class CompanyProfileActivity : AppCompatActivity() {

    private lateinit var b: ActivityCompanyProfileBinding
    private lateinit var db: AppDatabase
    private val coroutine = CoroutineScope(Dispatchers.IO)
    lateinit var us: UserItem
    lateinit var user: UserItem


    val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->

        if(result.resultCode == 4){
            var data = result.data
            if (data != null) {
                user = data.getParcelableExtra<UserItem>("dataBaru")!!

                loadprofile(user)

//                Toast.makeText(this, "load profil baru", Toast.LENGTH_SHORT).show()

            }


        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_company_profile)

        b = ActivityCompanyProfileBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        db= AppDatabase.Build(this)


        b.tvCompanyProfileKoneksi.setVisibility(View.GONE)
        var cek = -1

        try {
            cek = intent.getIntExtra("Action", -1)
        }catch (e:Exception){

        }


            try {
                if (cek == -1){
                    us = intent.getParcelableExtra<UserItem>("userLogin")!!
                    coroutine.launch {
                        user = db.userDao.getUserByEmail(us.email)!!
                        loadprofile(user)
                    }


//                    Toast.makeText(this, "true", Toast.LENGTH_SHORT).show()
                }
                else{
                    user = intent.getParcelableExtra<UserItem>("userLogin")!!

//
                    loadprofile(user)
                    b.imgCompanyProfileEditProfile.setVisibility(View.GONE)
//                    Toast.makeText(this, "false", Toast.LENGTH_SHORT).show()
                }

            }catch (e:Exception){
//            Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
            }



        b.imgCompanyProfileEditProfile.setOnClickListener {
            val i : Intent = Intent(this, EditProfileUserActivity::class.java)
            i.putExtra("userLogin",user)
            startActivity(i)
        }


    }


    fun loadprofile(usr:UserItem){

        b.tvCompanyProfileNama.text = "${usr.name}"

        if (usr.description == null){
            b.tvCompanyProfileDeskripsi.text = "Deskripsi : Kosong"
        }
        else{
            b.tvCompanyProfileDeskripsi.text = "${usr.description}"
        }
        if (usr.lokasi == null){
            b.tvCompanyProfileLokasi.text = "Lokasi : Belum diatur"
        }else{
            b.tvCompanyProfileLokasi.text = "Lokasi : ${usr.lokasi}"
        }
        if (usr.founded == null){
            b.tvCompanyProfileFounded.text = "Founded : Belum diatur"
        }else{
            b.tvCompanyProfileFounded.text = "Founded: ${usr.founded}"
        }
        if (usr.industry == null){
            b.tvCompanyProfileIndustry.text = "Industry : Belum diatur"
        }else{
            b.tvCompanyProfileIndustry.text = "Industry : ${usr.industry}"
        }
        if(usr.image!=null){
            //runOnUiThread { Toast.makeText(this, "ambil gambar", Toast.LENGTH_SHORT).show() }
            val i= URL(env.API_URL.substringBefore("/api/")+usr.image).openStream()
            val image= BitmapFactory.decodeStream(i)
            runOnUiThread { b.imgCompanyProfileProfile.setImageBitmap(image) }
        }
    }
}