package com.example.gawekerjo.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityAddBahasaBinding
import com.example.gawekerjo.model.language.LanguageItem
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.model.userlanguage.UserLanguage
import com.example.gawekerjo.repository.CountryRepository
import com.example.gawekerjo.repository.LanguageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AddBahasaActivity : AppCompatActivity() {

    private lateinit var b: ActivityAddBahasaBinding
    private lateinit var db : AppDatabase
    private val coroutine = CoroutineScope(Dispatchers.IO)

    private lateinit var countryRepo : CountryRepository
    private lateinit var us : UserItem
    private lateinit var langrepo : LanguageRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        b = ActivityAddBahasaBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        db = AppDatabase.Build(this)
//        setContentView(R.layout.activity_add_bahasa)
        langrepo = LanguageRepository(db)

        try {

            us = intent.getParcelableExtra<UserItem>("userLogin")!!
        }catch (e:Exception){
            Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
        }


        // kembali
        b.imgTambahBahasaBack.setOnClickListener {
            if(b.loadModal.visibility == View.VISIBLE){
                return@setOnClickListener
            }

            finish()
        }

        b.loadModal.visibility = View.VISIBLE
        disableEnableControls(false,b.linearLayout)

        countryRepo = CountryRepository(db)
        loadLanguage(this)

        b.btnTambahBahasaSimpan.setOnClickListener {
//            if(b.loadModal.visibility == View.VISIBLE){
//                return@setOnClickListener
//            }
            try {
                var user_id = us.id
                var bahasa = b.autoCompleteTextView.text.toString()
                var level = ""
                if (b.rbDasar.isChecked){
                    level = b.rbDasar.text.toString()
                }else if (b.rbMenengah.isChecked){
                    level = b.rbMenengah.text.toString()
                }else if(b.rbProfessional.isChecked){
                    level = b.rbProfessional.text.toString()
                }else if(b.rbFasih.isChecked){
                    level = b.rbFasih.text.toString()
                }

                langrepo.tambahbahasa(this, user_id, bahasa, level)

            }catch (e:Exception){
                Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
            }




        }
    }

    fun loadLanguage(mc : AddBahasaActivity){
        coroutine.launch {
            var listLang : List<LanguageItem> = db.languageDao.fetchLanguage()
            if(listLang.size <= 0){
                countryRepo.getAllLanguages(mc, db)
                listLang = db.languageDao.fetchLanguage()
            }else{
                runOnUiThread {
                    b.loadModal.visibility = View.GONE
                    disableEnableControls(true,b.linearLayout)

                    var stringLang : ArrayList<String> = arrayListOf()
                    for (s : LanguageItem in listLang){
                        stringLang.add(s.name)
                    }

                    val arrAdapter = ArrayAdapter(this@AddBahasaActivity,
                        com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                    stringLang)

                    b.autoCompleteTextView.setAdapter(arrAdapter)
                }
            }
        }
    }

    fun addBahasaCallBack(result: UserLanguage){
        if (result.status == 200){
            Toast.makeText(this, "${result.message}", Toast.LENGTH_SHORT).show()
            val i = Intent()
            setResult(3, intent)
            finish()

            this.finish()
        }
        else{
            Toast.makeText(this, "${result.message}", Toast.LENGTH_SHORT).show()
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


}