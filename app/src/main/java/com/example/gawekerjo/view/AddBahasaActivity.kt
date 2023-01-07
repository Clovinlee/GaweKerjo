package com.example.gawekerjo.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityAddBahasaBinding
import com.example.gawekerjo.model.LanguageItem
import com.example.gawekerjo.repository.CountryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AddBahasaActivity : AppCompatActivity() {

    private lateinit var b: ActivityAddBahasaBinding
    private lateinit var db : AppDatabase
    private val coroutine = CoroutineScope(Dispatchers.IO)

    private lateinit var countryRepo : CountryRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        b = ActivityAddBahasaBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        db = AppDatabase.Build(this)
//        setContentView(R.layout.activity_add_bahasa)


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
            if(b.loadModal.visibility == View.VISIBLE){
                return@setOnClickListener
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
        val kemahiran = resources.getStringArray(R.array.kemahiran)
        val arrayAdapter = ArrayAdapter(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, kemahiran)
        b.autoCompleteTextView.setAdapter(arrayAdapter)
    }
}