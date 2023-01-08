package com.example.gawekerjo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityEditProfileUserBinding
import com.example.gawekerjo.model.country.CountryItem
import com.example.gawekerjo.model.user.User
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.repository.AccountRepository
import com.example.gawekerjo.repository.CountryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditProfileUserActivity : AppCompatActivity() {

    private lateinit var b: ActivityEditProfileUserBinding
    private lateinit var db : AppDatabase
    private val coroutine = CoroutineScope(Dispatchers.IO)
    private lateinit var us : UserItem
    private lateinit var user : UserItem

    private lateinit var countryRepo : CountryRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityEditProfileUserBinding.inflate(layoutInflater)
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

        b.btnEditProfileuserSimpan.setOnClickListener {
            val accountRepo= AccountRepository(db)
            val name = b.etEditProfileUserNama.text.toString()
            val des = b.etEditProfileUserDeskripsi.text.toString()
            val notelp = b.etEditProfileUserNoTelp.text.toString()
            val id = user.id
            accountRepo.editprofile(this,id, name, des, notelp )

            Toast.makeText(this, "simpan", Toast.LENGTH_SHORT).show()
//            finish()
        }



        //kembali
        b.imgEditProfileUserBack.setOnClickListener {
            finish()
        }

        b.loadModal.visibility = View.VISIBLE
        disableEnableControls(false,b.linearLayoutEditProfile)
        countryRepo = CountryRepository(db)
        loadCountry(this)
    }

    fun loadCountry(mc : EditProfileUserActivity){
        coroutine.launch {
            var listCountry : List<CountryItem> = db.countryDao.fetchCountry()
            if(listCountry.size <= 0){
                countryRepo.getAllCountry(mc, db)
                listCountry = db.countryDao.fetchCountry()
            }else{
                runOnUiThread {
                    b.loadModal.visibility = View.GONE
                    disableEnableControls(true,b.linearLayoutEditProfile)

                    var stringCountry : ArrayList<String> = arrayListOf()

                    for (c : CountryItem in listCountry){
                        stringCountry.add(c.name)
                    }

                    val arrAdapter = ArrayAdapter(this@EditProfileUserActivity,
                        com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                        stringCountry)

                    b.autoCompleteTextView.setAdapter(arrAdapter)
                }
            }
        }
    }

    private fun disableEnableControls(enable: Boolean, vg: ViewGroup) {
        for (i in 0 until vg.childCount) {
            val child = vg.getChildAt(i)
            child.isEnabled = enable
            if (child is ViewGroup) {
                disableEnableControls(enable, child)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // untuk isi dropdown
//        val kemahiran = resources.getStringArray(R.array.kemahiran)
//        val arrayAdapter = ArrayAdapter(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, kemahiran)
//        b.autoCompleteTextView.setAdapter(arrayAdapter)
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

    fun balek(result:User){

        if(result.status == 200){
            var i : Intent = Intent(this, UserprofileActivity::class.java)
            i.putExtra("userlogin",result.data[0])
            startActivity(i)
            this.finish()
        }else{
            Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
        }
    }
}