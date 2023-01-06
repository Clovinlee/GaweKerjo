package com.example.gawekerjo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityAddKeahlianBinding
import com.example.gawekerjo.databinding.ActivityAddPendidikanBinding
import com.example.gawekerjo.model.skill.Skill
import com.example.gawekerjo.model.skill.SkillItem
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.model.userskill.UserSkill
import com.example.gawekerjo.repository.AccountRepository
import com.example.gawekerjo.repository.SkillRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddKeahlianActivity : AppCompatActivity() {

    private lateinit var b: ActivityAddKeahlianBinding
    private lateinit var db : AppDatabase
    private val coroutine = CoroutineScope(Dispatchers.IO)
    private lateinit var skillrepo : SkillRepository
    private lateinit var listskill : MutableList<SkillItem>
    private lateinit var listnama : ArrayList<String>
    private lateinit var us : UserItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAddKeahlianBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        db = AppDatabase.Build(this)
        listskill = mutableListOf()
        listnama = ArrayList()

        skillrepo = SkillRepository(db)


        try {

            us = intent.getParcelableExtra<UserItem>("userLogin")!!
        }catch (e:Exception){
            Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
        }


        coroutine.launch {

            listskill.addAll(db.skillDao.getAllSkill().toList())

            for (i in 0 until listskill.size){
                listnama.add(listskill[i].name.toString())
            }

        }

        b.btnTambahKeahlianSimpan.setOnClickListener {
            val user_id =us.id
            var skil = b.autoCompleteTextView.text.toString()
            var skill_id= 0
            for (i in 0 until listskill.size){
                if (listskill[i].name == skil){
                    skill_id = listskill[i].id
                }
            }
            if (skil!="Choose"){
                skillrepo.tambahskill(this, skill_id, user_id)
//

//                finish()
            }
            else{

                    Toast.makeText(this, "Pilih Skill dahulu", Toast.LENGTH_SHORT).show()

            }

        }

        //kembali
        b.imgTambahKeahlianBack.setOnClickListener {
            finish()
//            try {
//
//                Toast.makeText(this, "${listnama.size}", Toast.LENGTH_SHORT).show()
//            }catch (e:Exception){
//                Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
//            }
        }
    }

    fun addKeahlianCallBack(result: UserSkill){
        if (result.status == 200){
            Toast.makeText(this, "${result.message}", Toast.LENGTH_SHORT).show()
            val i = Intent()
            setResult(1, intent)
            finish()


            this.finish()
        }
        else{
            Toast.makeText(this, "${result.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()

        val arrayAdapter = ArrayAdapter(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, listnama)
        b.autoCompleteTextView.setAdapter(arrayAdapter)

    }




}