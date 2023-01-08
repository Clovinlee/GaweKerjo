package com.example.gawekerjo.repository

import android.util.Log
import com.example.gawekerjo.api.CountryApi
import com.example.gawekerjo.api.RetrofitClient
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.model.language.LanguageItem
import com.example.gawekerjo.model.country.Country
import com.example.gawekerjo.model.country.CountryItem
import com.example.gawekerjo.view.AddBahasaActivity
import com.example.gawekerjo.view.EditProfileUserActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class CountryRepository(var db : AppDatabase) {
    // BASE URL
    private val CBASE_URL : String = "https://restcountries.com/v3.1/"
    private val coroutine = CoroutineScope(Dispatchers.IO)
    var rc : Retrofit = RetrofitClient.getRetrofit(CBASE_URL)

    fun getAllCountry(mc : EditProfileUserActivity, db : AppDatabase){
        var rc_country : Call<List<Country>> = rc.create(CountryApi::class.java).getAllCountries()

        rc_country.enqueue(object : Callback<List<Country>>{
            override fun onResponse(
                call: Call<List<Country>>,
                response: Response<List<Country>>
            ) {
                var responseBody = response.body()
                if (responseBody != null) {
                    var listCountries : ArrayList<String> = arrayListOf()
                    for (c : Country in responseBody){
//                        Log.d("CCDCOUNTRY","COUNTRY NAME COMMON : "+c.name!!.common.toString())
//                        Log.d("CCD","COUNTRY LANGUAGE : "+c.languages!!.values.elementAt(0).toString())
//                        if(c.languages.isEmpty()){
//                            Log.d("CCDCOUNTRY","LANGUAGE : []")
//                        }else{
//                            Log.d("CCDCOUNTRY","COUNTRY LANGUAGE : "+c.languages!!.values.first().toString())
//                            Log.d("CCDCOUNTRY","COUNTRY LANGUAGE IN ARRAY : "+c.languages!!.values.toString())
//                        }
//                        Log.d("CCD","=======================================")

                        listCountries.add(c.name!!.common.toString())
                    }

                    listCountries.sort()
                    coroutine.launch {
                        db.countryDao.clear()
                        for (x in 1..listCountries.size){
                            var c : CountryItem = CountryItem(listCountries[x-1])
                            db.countryDao.insertCountry(c)
                        }
                        mc.loadCountry(mc)
                    }

                }
            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                Log.d("CCD","Fail to get country data")
            }

        })
    }

    fun getAllLanguages(mc : AddBahasaActivity, db : AppDatabase){
        var rc_country : Call<List<Country>> = rc.create(CountryApi::class.java).getAllCountries()

        rc_country.enqueue(object : Callback<List<Country>>{
            override fun onResponse(
                call: Call<List<Country>>,
                response: Response<List<Country>>
            ) {
                var responseBody = response.body()
                if (responseBody != null) {
                    var listLanguages : ArrayList<String> = arrayListOf()
                    for (c : Country in responseBody){
//                        Log.d("CCDCOUNTRY","COUNTRY NAME COMMON : "+c.name!!.common.toString())
//                        Log.d("CCD","COUNTRY LANGUAGE : "+c.languages!!.values.elementAt(0).toString())
//                        if(c.languages.isEmpty()){
//                            Log.d("CCDCOUNTRY","LANGUAGE : []")
//                        }else{
//                            Log.d("CCDCOUNTRY","COUNTRY LANGUAGE : "+c.languages!!.values.first().toString())
//                            Log.d("CCDCOUNTRY","COUNTRY LANGUAGE IN ARRAY : "+c.languages!!.values.toString())
//                        }
//                        Log.d("CCD","=======================================")

                        if(!c.languages.isEmpty() && !listLanguages.contains(c.languages!!.values.first())){
                            listLanguages.add(c.languages!!.values.first())
                        }
                    }

                    listLanguages.sort()
                    coroutine.launch {
                        db.languageDao.clear()
                        for (x in 1..listLanguages.size){
                            var l : LanguageItem = LanguageItem(x-1,listLanguages[x-1], "", "")
                            db.languageDao.insertLanguage(l)
                        }
                        mc.loadLanguage(mc)
                    }

                }
            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                Log.d("CCD","Fail to get country data")
            }

        })
    }
}