package com.example.gawekerjo.view

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityEditProfileUserBinding
import com.example.gawekerjo.databinding.ActivityUserprofileBinding
import com.example.gawekerjo.env
import com.example.gawekerjo.model.country.CountryItem
import com.example.gawekerjo.model.user.User
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.repository.AccountRepository
import com.example.gawekerjo.repository.CountryRepository
import com.example.gawekerjo.utility.UploadUtility
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class EditProfileUserActivity : AppCompatActivity() {

    private lateinit var b: ActivityEditProfileUserBinding
    private lateinit var db : AppDatabase
    private val coroutine = CoroutineScope(Dispatchers.IO)
    private lateinit var us : UserItem
    private lateinit var user : UserItem
    private var urlgambar:Uri?=null

    val REQUEST_CODE = 100

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
            tipeuser(user)
            loadprofile(user)
        }
        b.btneditprofilegambar.setOnClickListener { openGalleryForImage() }
        b.btnEditProfileuserSimpan.setOnClickListener {
            if (urlgambar!=null){
                if (askForPermissions()){
                    user.image="/storage/user/${UploadUtility(this).uploadFile(urlgambar!!,this,user.id.toString())}"
                }else{
                    EditDataUser()
                }
            }
            EditDataUser()
//            Toast.makeText(this, "simpan", Toast.LENGTH_SHORT).show()
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

    fun tipeuser(usr: UserItem){
        if (usr.type == "1"){
            b.tvfounded.setVisibility(View.GONE)
            b.etEditProfileUserFounded.setVisibility(View.GONE)
            b.tvindustri.setVisibility(View.GONE)
            b.etEditProfileUserIndustri.setVisibility(View.GONE)
        }
        else{
            b.tvtanggallahir.setVisibility(View.GONE)
            b.etdEditProfileUserTanggalLahir.setVisibility(View.GONE)
            b.tvjeniskelamin.setVisibility(View.GONE)
            b.spinnerEditProfileUserKelamin.setVisibility(View.GONE)
        }
    }

    fun EditDataUser(){
        val accountRepo= AccountRepository(db)
        val name = b.etEditProfileUserNama.text.toString()
        val des = b.etEditProfileUserDeskripsi.text.toString()
        val notelp = b.etEditProfileUserNoTelp.text.toString()
        var gender = ""
        if (b.spinnerEditProfileUserKelamin.selectedItem == "Pria"){
            gender = "L"
        }else{
            gender = "P"
        }
        var negara = b.autoCompleteTextView.text.toString()
        var tgl = b.etdEditProfileUserTanggalLahir.text.toString()
        var founded = b.etEditProfileUserFounded.text.toString()
        var indistry = b.etEditProfileUserIndustri.text.toString()

//        Toast.makeText(this, "${b.spinnerEditProfileUserKelamin.selectedItem}", Toast.LENGTH_SHORT).show()
        val id = user.id
        accountRepo.editprofile(this,id, name, des, notelp, gender, tgl, negara, founded, indistry )
    }
    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            urlgambar=data?.data
            b.ivEditProfilePicture.setImageURI(urlgambar!!) // handle chosen image
        }
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

    private fun isPermissionsAllowed(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    private fun askForPermissions(): Boolean {
        if (!isPermissionsAllowed()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this as Activity,Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(this as Activity,arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),REQUEST_CODE)
            }
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<String>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission is granted, you can perform your operation here
                } else {
                    // permission is denied, you can ask for permission again, if you want
                      askForPermissions()
                }
                return
            }
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton("App Settings",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    // send to app settings if permission is denied permanently
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri = Uri.fromParts("package", getPackageName(), null)
                    intent.data = uri
                    startActivity(intent)
                })
            .setNegativeButton("Cancel",null)
            .show()
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
            var temp = usr.birthdate
            var dt = temp?.substringBeforeLast("T")


            b.etdEditProfileUserTanggalLahir.setText("${dt}")
        }
        if(usr.lokasi!=null){
            b.autoCompleteTextView.setText("${usr.lokasi}")
        }
        if (usr.image!=null){
            val i= URL(env.API_URL.substringBefore("/api/")+usr.image).openStream()
            val image= BitmapFactory.decodeStream(i)
            runOnUiThread { b.ivEditProfilePicture.setImageBitmap(image) }
        }



    }



    fun balek(result:User){

        if(result.status == 200){
            if (result.data[0].type == "1"){
                var i : Intent = Intent(this, UserprofileActivity::class.java)
                i.putExtra("userLogin",result.data[0])
                startActivity(i)
                this.finish()
            }
            else{
                var i : Intent = Intent(this, CompanyProfileActivity::class.java)
                i.putExtra("userLogin",result.data[0])
                startActivity(i)
                this.finish()
            }


        }else{
            Toast.makeText(this, "${result.message}", Toast.LENGTH_SHORT).show()
        }
    }
}