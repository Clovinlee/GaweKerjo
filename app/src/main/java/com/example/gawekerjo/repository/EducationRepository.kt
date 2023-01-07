package com.example.gawekerjo.repository

import android.util.Log
import com.example.gawekerjo.api.EducationApi
import com.example.gawekerjo.api.RetrofitClient
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.model.education.Education
import com.example.gawekerjo.view.AddPendidikanActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.Date

class EducationRepository (var db: AppDatabase){
    private val coroutine = CoroutineScope(Dispatchers.IO)
    var rc : Retrofit = RetrofitClient.getRetrofit()

    fun addEducation(mc: AddPendidikanActivity,id:Int, nama: String, tgl_mulai:String, tgl_akhir: String, nilai: String){

        var rc_edu : Call<Education> = rc.create(EducationApi::class.java).addEducation(id, nama , tgl_mulai, tgl_akhir, nilai)

        rc_edu.enqueue(object :Callback<Education>{
            override fun onResponse(call: Call<Education>, response: Response<Education>) {
                val responseBody = response.body()
                if (responseBody != null){
                    if (responseBody.status == 200){
                        val edu = responseBody.data[0]

                        coroutine.launch {
                            db.educationDao.insertEducation(responseBody.data[0])
                        }
                    }
                    mc.addPendidikanCallBack(responseBody)
                }

            }

            override fun onFailure(call: Call<Education>, t: Throwable) {
                Log.d("CCD","Error tambah education")
                Log.d("CCD",t.message.toString())
            }

        })
    }
}