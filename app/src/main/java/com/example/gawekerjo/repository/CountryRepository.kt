package com.example.gawekerjo.repository

import android.util.Log
import com.example.gawekerjo.api.CountryApi
import com.example.gawekerjo.api.RetrofitClient
import com.example.gawekerjo.api.UserApi
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.model.UserItem
import com.example.gawekerjo.model.country.Country
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class CountryRepository(var db : AppDatabase) {
    // BASE URL
    private val BASE_URL : String = "https://restcountries.com/v3.1/"
    private val coroutine = CoroutineScope(Dispatchers.IO)
    var rc : Retrofit = RetrofitClient.getRetrofit(BASE_URL)

    fun getAllCountries(){
        var rc_country : Call<List<Country>> = rc.create(CountryApi::class.java).getAllCountries()

        rc_country.enqueue(object : Callback<List<Country>>{
            override fun onResponse(
                call: Call<List<Country>>,
                response: Response<List<Country>>
            ) {
                var responseBody = response.body()
                if (responseBody != null) {
                    for (c : Country in responseBody){
                        Log.d("CCD",c.name!!.common.toString())
                        Log.d("CCD",c.languages!!.values.elementAt(0).toString())
                    }
                }
            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                Log.d("CCD","Fail to get country data")
            }

        })
    }
}