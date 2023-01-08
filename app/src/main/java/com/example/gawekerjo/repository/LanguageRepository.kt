package com.example.gawekerjo.repository

import android.util.Log
import com.example.gawekerjo.api.LanguageApi
import com.example.gawekerjo.api.RetrofitClient
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.model.userlanguage.UserLanguage
import com.example.gawekerjo.model.userlanguage.UserLanguageItem
import com.example.gawekerjo.view.AddBahasaActivity
import com.example.gawekerjo.view.UserprofileActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class LanguageRepository(var db: AppDatabase) {
    private val coroutine = CoroutineScope(Dispatchers.IO)
    var rc : Retrofit = RetrofitClient.getRetrofit()

    fun getuserLang(mc: UserprofileActivity, iduser: Int){

        var rc_lang: Call<UserLanguage> = rc.create(LanguageApi::class.java).getUserLanguages(null , iduser)
        rc_lang.enqueue(object : Callback<UserLanguage> {
            override fun onResponse(call: Call<UserLanguage>, response: Response<UserLanguage>) {
                val responseBody = response.body()
                if (responseBody != null){
                    if (responseBody.status == 200 && responseBody.data.size > 0){

                        coroutine.launch {
                            var lg : UserLanguageItem?=null
                            db.userlanguageDao.clear()
                            for (i in 0 until responseBody.data.size){
                                lg = responseBody.data[i]
                                db.userlanguageDao.insertUserLanguage(lg)
                            }

                        }
                    }
                    mc.loadlang(mc, true)
                }
            }

            override fun onFailure(call: Call<UserLanguage>, t: Throwable) {
                Log.d("CCD", "Error getting bahasa")
                Log.d("CCD", t.message.toString())
            }

        })
    }

    fun tambahbahasa(mc: AddBahasaActivity, iduser: Int, bahasa: String, level: String){
        var rc_lang: Call<UserLanguage> = rc.create(LanguageApi::class.java).addUserLanguages(iduser, bahasa, level)

        rc_lang.enqueue(object : Callback<UserLanguage> {
            override fun onResponse(call: Call<UserLanguage>, response: Response<UserLanguage>) {
                val responseBody = response.body()
                if (responseBody != null){
                    if (responseBody.status == 200){
                        val lang = responseBody.data[0]

                        coroutine.launch {
                            db.userlanguageDao.insertUserLanguage(responseBody.data[0])
                        }
                    }
                    mc.addBahasaCallBack(responseBody)
                }
            }

            override fun onFailure(call: Call<UserLanguage>, t: Throwable) {
                Log.d("CCD","Error tambah bahasa")
                Log.d("CCD",t.message.toString())
            }

        })
    }

    fun deleteuserlang(mc: UserprofileActivity, id:Int){
        var rc_lang : Call<UserLanguage> = rc.create(LanguageApi::class.java).deleteUserLanguages(id)

        rc_lang.enqueue(object: Callback<UserLanguage>{
            override fun onResponse(call: Call<UserLanguage>, response: Response<UserLanguage>) {
                val responseBody = response.body()
                if (responseBody != null){
                    if (responseBody.status == 200){
                        val lang = responseBody.data[0]
                        coroutine.launch {
                            db.userlanguageDao.deleteUserLanguage(lang)
                        }
                    }
                    mc.deletelangcallback(responseBody)
                }
            }

            override fun onFailure(call: Call<UserLanguage>, t: Throwable) {
                Log.d("CCD", "Error delete language user")
                Log.d("CCD", t.message.toString())
            }

        })
    }
}