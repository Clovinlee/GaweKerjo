package com.example.gawekerjo.repository

import android.util.Log
import com.example.gawekerjo.api.RetrofitClient
import com.example.gawekerjo.api.SkillApi
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.model.skill.Skill
import com.example.gawekerjo.model.skill.SkillItem
import com.example.gawekerjo.model.userskill.UserSkill
import com.example.gawekerjo.model.userskill.UserSkillItem
import com.example.gawekerjo.view.AddKeahlianActivity
import com.example.gawekerjo.view.HomeActivity
import com.example.gawekerjo.view.UserprofileActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SkillRepository(var db : AppDatabase){
    private val coroutine = CoroutineScope(Dispatchers.IO)
    var rc : Retrofit = RetrofitClient.getRetrofit()

    fun getAllSkill(mc: HomeActivity){
        var rc_skil : Call<Skill> = rc.create(SkillApi::class.java).getSkills(null, null)

        rc_skil.enqueue(object :Callback<Skill>{
            override fun onResponse(call: Call<Skill>, response: Response<Skill>) {
                coroutine.launch {
                    val responseBody = response.body()
                    var skl : SkillItem? = null
                    if (responseBody != null){
                        if (responseBody.status == 200 && responseBody.data.size > 0){
                            db.skillDao.clear()
                            for (i in 0 until responseBody.data.size){

                                skl = responseBody.data[i]
                                db.skillDao.insertSkill(skl)

                            }

                        }
                    }
                }
            }

            override fun onFailure(call: Call<Skill>, t: Throwable) {
                Log.d("CCD", "Error getting skill")
                Log.d("CCD", t.message.toString())
            }

        })


    }

    fun tambahskill(mc: AddKeahlianActivity, idskil:Int, iduser:Int){
        var rc_skill : Call<UserSkill> = rc.create(SkillApi::class.java).addUserSkill(iduser, idskil)

        rc_skill.enqueue(object : Callback<UserSkill>{
            override fun onResponse(call: Call<UserSkill>, response: Response<UserSkill>) {
                val responseBody = response.body()
                if (responseBody != null){
                    if (responseBody.status == 200){
                        val skil = responseBody.data[0]

                        coroutine.launch {
//                            db.userskillDao.clear()
                            db.userskillDao.insertUserSkill(responseBody.data[0])
                        }
                    }
                    mc.addKeahlianCallBack(responseBody)

                }
            }

            override fun onFailure(call: Call<UserSkill>, t: Throwable) {
                Log.d("CCD","Error tambah keahlian")
                Log.d("CCD",t.message.toString())
            }

        })
    }

    fun getUserSkill(mc: HomeActivity, iduser: Int, idskil: Int?){
        var rc_skil : Call<UserSkill> = rc.create(SkillApi::class.java).getUserSkill(null, iduser, idskil)

        rc_skil.enqueue(object :Callback<UserSkill>{
            override fun onResponse(call: Call<UserSkill>, response: Response<UserSkill>) {
                coroutine.launch {
                    val responseBody = response.body()
                    var skl : UserSkillItem? = null
                    if (responseBody != null){
                        if (responseBody.status == 200 && responseBody.data.size > 0){
                            db.userskillDao.clear()
                            for (i in 0 until responseBody.data.size){

                                skl = responseBody.data[i]
                                db.userskillDao.insertUserSkill(skl)

                            }

                        }
                    }
                }
            }

            override fun onFailure(call: Call<UserSkill>, t: Throwable) {
                Log.d("CCD", "Error getting skill")
                Log.d("CCD", t.message.toString())
            }

        })
    }


    fun deleteuserskill(mc:UserprofileActivity, id:Int){
        var rc_skill : Call<UserSkill> = rc.create(SkillApi::class.java).deleteUserSkill(id)

        rc_skill.enqueue(object: Callback<UserSkill>{
            override fun onResponse(call: Call<UserSkill>, response: Response<UserSkill>) {
                val responseBody = response.body()
                if (responseBody != null){
                    if (responseBody.status == 200){
                        val skil = responseBody.data[0]
                        coroutine.launch {
                            db.userskillDao.deleteUserSkill(skil)
                        }
                    }

                    mc.deletecallback(responseBody)
                }
            }

            override fun onFailure(call: Call<UserSkill>, t: Throwable) {
                Log.d("CCD", "Error delete skill")
                Log.d("CCD", t.message.toString())
            }

        })
    }


}
