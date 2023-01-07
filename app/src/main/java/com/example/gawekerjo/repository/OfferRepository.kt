package com.example.gawekerjo.repository

import android.util.Log
import com.example.gawekerjo.api.OfferApi
import com.example.gawekerjo.api.RetrofitClient
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.model.Offer.Offer
import com.example.gawekerjo.model.Offer.OfferItem
import com.example.gawekerjo.model.country.Country
import com.example.gawekerjo.view.OffersFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.*

class OfferRepository(var db : AppDatabase) {

    private val coroutine = CoroutineScope(Dispatchers.IO)
    var rc : Retrofit = RetrofitClient.getRetrofit()

    fun searchOffer(mc : OffersFragment, title : String?){
        var rc_offer : Call<Offer> = rc.create(OfferApi::class.java).searchOffer(title)

        rc_offer.enqueue(object : Callback<Offer>{
            override fun onResponse(call: Call<Offer>, response: Response<Offer>) {
                var rbody = response.body()!!
                if(rbody.status == 200){
                    coroutine.launch {
                        db.offerDao.clear()
                        for (o : OfferItem in rbody.data){
                            db.offerDao.insertOffer(o)
                        }
                        mc.loadData(true)
                    }
                }
            }

            override fun onFailure(call: Call<Offer>, t: Throwable) {
                Log.d("CCD","Fail to get offer data")
            }

        })
    }
}