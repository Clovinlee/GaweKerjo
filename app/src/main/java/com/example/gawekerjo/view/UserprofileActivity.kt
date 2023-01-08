package com.example.gawekerjo.view

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityUserprofileBinding
import com.example.gawekerjo.model.education.EducationItem
import com.example.gawekerjo.model.language.LanguageItem
import com.example.gawekerjo.model.skill.SkillItem
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.model.userlanguage.UserLanguageItem
import com.example.gawekerjo.model.userskill.UserSkill
import com.example.gawekerjo.model.userskill.UserSkillItem
import com.example.gawekerjo.repository.EducationRepository
import com.example.gawekerjo.repository.LanguageRepository
import com.example.gawekerjo.repository.SkillRepository
import com.example.gawekerjo.view.adapter.BahasaAdapter
import com.example.gawekerjo.view.adapter.KeahlianListAdapter
import com.example.gawekerjo.view.adapter.OnRecyclerViewItemClickListener
import com.example.gawekerjo.view.adapter.PendidikanAdapter
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
    private lateinit var edurepo : EducationRepository
    private lateinit var langrepo: LanguageRepository
    private lateinit var keahlianAdapter: KeahlianListAdapter
    private lateinit var pendidikanAdapter: PendidikanAdapter
    private lateinit var languageAdapter: BahasaAdapter
    private lateinit var listskill : MutableList<UserSkillItem>
    private lateinit var listnama: MutableList<SkillItem>
    private lateinit var listpendidikan: MutableList<EducationItem>
    private lateinit var listlang: MutableList<UserLanguageItem>


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
        else if(result.resultCode == 2){

//            loadedu()
            reloadpendidikan()
            pendidikanAdapter.notifyDataSetChanged()
        }
        else if (result.resultCode == 3){
            reloadlang()
            languageAdapter.notifyDataSetChanged()

        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        b = ActivityUserprofileBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        db = AppDatabase.Build(this)
        skillrepo = SkillRepository(db)
        edurepo = EducationRepository(db)
        langrepo = LanguageRepository(db)


        listskill = mutableListOf()
        listnama = mutableListOf()
        listpendidikan = mutableListOf()
        listlang = mutableListOf()

//        b.loadModal.visibility = View.VISIBLE
//        disableEnableControls(false, b.linearlayout)




        try {

            us = intent.getParcelableExtra<UserItem>("userLogin")!!
        }catch (e:Exception){
            Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
        }


        keahlianAdapter = KeahlianListAdapter(listskill,listnama, R.layout.layout_list_keahlian, this@UserprofileActivity){

        }
        setLayoutManagerkeahlian()
        keahlianAdapter.setOnItemClickListener(object :OnRecyclerViewItemClickListener{
            override fun OnClick(view: View, position: Int) {
                showDeleteDialog(position)
            }
        })
        pendidikanAdapter = PendidikanAdapter(listpendidikan, R.layout.layout_listpendidikan, this@UserprofileActivity){

        }
        setLayoutManagerpendidikan()

        languageAdapter = BahasaAdapter(listlang, R.layout.layout_list_bahasa, this@UserprofileActivity){

        }
        setLayoutManagerLanguage()

        loadskill(this)
        loadpendidikan(this)
        loadlang(this)



        coroutine.launch {
            user = db.userDao.getUserByEmail(us.email)!!
            loadprofile(user)
            listskill.clear()
//            listskill.addAll(db.userskillDao.getAllUserSkill().toList())

        }
        //masuk edit profile

        b.imgUserProfileEditProfile.setOnClickListener {
            val i : Intent = Intent(this, EditProfileUserActivity::class.java)
            i.putExtra("userLogin",user)
            startActivity(i)
        }

        // masuk tambah pendidikan
        b.imgUserProfileTambahPendidikan.setOnClickListener {

            val i : Intent = Intent(this, AddPendidikanActivity::class.java)
            i.putExtra("userLogin",user)
            launcher.launch(i)

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
            i.putExtra("userLogin",user)
            launcher.launch(i)
        }
    }

    fun showDeleteDialog(position: Int){
        var dialog : Dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.delete_dialog)

        var btnyes: Button = dialog.findViewById(R.id.btnDeleteDialogYes)
        var btnno: Button = dialog.findViewById(R.id.btnDeleteDialogNo)
        btnyes.setOnClickListener {
            skillrepo.deleteuserskill(this@UserprofileActivity, listskill[position].id)
            dialog.dismiss()
        }

        btnno.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    fun loadskill(mc: UserprofileActivity){
        coroutine.launch {

            listskill.addAll(db.userskillDao.getAllUserSkill().toList())
            listnama.addAll(db.skillDao.getAllSkill().toList())
            if (listskill.size <= 0){
                skillrepo.getUserSkill(mc, us.id, null)
                load()
            }
            else{
                runOnUiThread {
                    load()
                    keahlianAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    fun loadpendidikan(mc: UserprofileActivity){

        coroutine.launch {
            listpendidikan.addAll(db.educationDao.getAllEducation().toList())
            if (listpendidikan.size <= 0){
                edurepo.getUserEdu(mc, null, us.id)
                reloadpendidikan()
            }
            else{
                runOnUiThread {
                    reloadpendidikan()
//                    b.loadModal.visibility = View.GONE
//                    disableEnableControls(true, b.linearlayout)
                    pendidikanAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    fun loadlang(mc: UserprofileActivity){
        coroutine.launch {
            listlang.addAll(db.userlanguageDao.getAllUserLanguage().toList())
            if (listlang.size <= 0){
                langrepo.getuserLang(mc, us.id)
                reloadlang()
            }else{
                runOnUiThread {
                    reloadlang()

                    languageAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    fun deletecallback(result: UserSkill){
        if (result.status == 200){
            Toast.makeText(this, "${result.message}", Toast.LENGTH_SHORT).show()

            load()

            keahlianAdapter.notifyDataSetChanged()
        }
        else{
            Toast.makeText(this, "${result.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun load(){
        listskill.clear()
        coroutine.launch {

            listskill.addAll(db.userskillDao.getAllUserSkill().toList())
        }

//        keahlianAdapter.notifyDataSetChanged()

    }

    fun reloadpendidikan(){
        listpendidikan.clear()
        coroutine.launch {
            listpendidikan.addAll(db.educationDao.getAllEducation().toList())
        }

//        pendidikanAdapter.notifyDataSetChanged()

    }

    fun reloadlang(){
        listlang.clear()
        coroutine.launch {
            listlang.addAll(db.userlanguageDao.getAllUserLanguage().toList())
        }

    }

    fun setLayoutManagerkeahlian(){
        val linearLayoutManagerkeahlian = LinearLayoutManager(this)
        b.rvuserProfileKeahlian.adapter = keahlianAdapter
        b.rvuserProfileKeahlian.layoutManager = linearLayoutManagerkeahlian


    }

    fun setLayoutManagerpendidikan(){
        val linearLayoutManagerpendidikan = LinearLayoutManager(this)
        b.rvUserProfilePendidikan.adapter = pendidikanAdapter
        b.rvUserProfilePendidikan.layoutManager = linearLayoutManagerpendidikan
    }

    fun setLayoutManagerLanguage(){
        val linearLayoutManagerLanguage = LinearLayoutManager(this)
        b.rvUserProfileBahasa.adapter = languageAdapter
        b.rvUserProfileBahasa.layoutManager = linearLayoutManagerLanguage
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