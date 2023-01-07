package com.example.gawekerjo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityAddPendidikanBinding
import com.example.gawekerjo.databinding.ActivityEditProfileUserBinding
import com.example.gawekerjo.model.education.Education
import com.example.gawekerjo.model.education.EducationItem
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.repository.EducationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class AddPendidikanActivity : AppCompatActivity() {

    private lateinit var b: ActivityAddPendidikanBinding
    private lateinit var db : AppDatabase
    private val coroutine = CoroutineScope(Dispatchers.IO)
    private lateinit var edurepo: EducationRepository
    private lateinit var listedu: MutableList<EducationItem>
    private lateinit var us: UserItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAddPendidikanBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        db = AppDatabase.Build(this)
        listedu = mutableListOf()
        edurepo = EducationRepository(db)

        try {

            us = intent.getParcelableExtra<UserItem>("userLogin")!!
        }catch (e:Exception){
            Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
        }

        b.btnTambahPendidikanSimpan.setOnClickListener {
            val user_id = us.id
            var nama = b.etTambahPendiidkanNama.text.toString()
            var tgl_mulai = b.etdTambahPendidikanTanggalMulai.text.toString()
            var tgl_akhir = b.etdTambahPendidikanTanggalBerakhir.text.toString()
            val nilai = b.etTambahPendidikanNilai.text.toString()

            edurepo.addEducation(this, user_id, nama, tgl_mulai, tgl_akhir, nilai)

//            Toast.makeText(this, "${tgl_mulai}", Toast.LENGTH_SHORT).show()
        }




        //kembali
        b.imgTambahPendidikanBack.setOnClickListener {
            finish()
        }
    }

    fun addPendidikanCallBack(result: Education){
        if (result.status == 200){
            Toast.makeText(this, "${result.message}", Toast.LENGTH_SHORT).show()
            val i = Intent()
            setResult(2, intent)
            finish()
        }
        else{
            Toast.makeText(this, "${result.message}", Toast.LENGTH_SHORT).show()
        }
    }
}