package com.example.gawekerjo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityUserprofileBinding
import com.example.gawekerjo.model.skill.SkillItem
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.model.userskill.UserSkillItem
import com.example.gawekerjo.repository.SkillRepository
import com.example.gawekerjo.view.adapter.KeahlianListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserprofileActivity : AppCompatActivity() {

    private lateinit var b: ActivityUserprofileBinding
    private lateinit var db : AppDatabase
    private val coroutine = CoroutineScope(Dispatchers.IO)
    lateinit var user : UserItem
    lateinit var us : UserItem
    private lateinit var skillrepo : SkillRepository
    private lateinit var keahlianAdapter: KeahlianListAdapter
    private lateinit var listskill : MutableList<UserSkillItem>
    private lateinit var listnama: MutableList<SkillItem>

    val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result: ActivityResult ->
        if (result.resultCode == 1){
//            skillrepo.getUserSkill(this, us.id, null)

            listskill.clear()
            coroutine.launch {

                listskill.addAll(db.userskillDao.getAllUserSkill().toList())
            }
            keahlianAdapter.notifyDataSetChanged()

        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        b = ActivityUserprofileBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        db = AppDatabase.Build(this)
        skillrepo = SkillRepository(db)


        listskill = mutableListOf()
        listnama = mutableListOf()



        try {

            us = intent.getParcelableExtra<UserItem>("userLogin")!!
        }catch (e:Exception){
            Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
        }

//        skillrepo.getUserSkill(this, us.id, null)
//        skillrepo.getAllSkill(this)


        coroutine.launch {
            user = db.userDao.getUserByEmail(us.email)!!
            loadprofile(user)
            listskill.clear()
            listskill.addAll(db.userskillDao.getAllUserSkill().toList())
            listnama.addAll(db.skillDao.getAllSkill().toList())
            keahlianAdapter = KeahlianListAdapter(listskill,listnama, R.layout.layout_list_keahlian, this@UserprofileActivity){

            }

            setLayoutManager()

            keahlianAdapter.notifyDataSetChanged()

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
            i.putExtra("userLogin",user)
            launcher.launch(i)
        }

        // masuk tambah bahasa
        b.imgUserProfileTambahBahasa.setOnClickListener {
            val i : Intent = Intent(this, AddBahasaActivity::class.java)
            startActivity(i)
        }



    }

    fun setLayoutManager(){
        val linearLayoutManager = LinearLayoutManager(this)
        b.rvuserProfileKeahlian.adapter = keahlianAdapter
        b.rvuserProfileKeahlian.layoutManager = linearLayoutManager
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