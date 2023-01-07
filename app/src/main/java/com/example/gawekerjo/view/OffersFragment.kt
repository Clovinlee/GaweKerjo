package com.example.gawekerjo.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.FragmentOffersBinding
import com.example.gawekerjo.model.Offer.OfferItem
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.repository.OfferRepository
import com.example.gawekerjo.view.adapter.RVAdapterJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OffersFragment(var mc : HomeActivity, var db : AppDatabase, var user : UserItem) : Fragment() {

    private lateinit var adapterJob : RVAdapterJob
    private lateinit var b : FragmentOffersBinding
    private val coroutine = CoroutineScope(Dispatchers.IO)
    private lateinit var ctx : Context
    private var listOffer : List<OfferItem> = listOf()

    private lateinit var offerRepo : OfferRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ctx = view.context
        offerRepo = OfferRepository(db)
        adapterJob = RVAdapterJob(this, R.layout.layout_rv_offer, listOffer, db)

        b.btnOfferSearch.setOnClickListener {
            if(b.loadModal.visibility == View.VISIBLE){
                return@setOnClickListener
            }

            listOffer = listOf()
            initRV()

            loadData(title = b.txtSearchJob.text.toString())
            b.loadModal.visibility = View.VISIBLE
        }

        b.loadModal.visibility = View.VISIBLE
        loadData()
    }

    fun loadData(fetched : Boolean = false, title : String? = null){
        coroutine.launch {
            listOffer = db.offerDao.fetch()
            if((listOffer.size == 0 && fetched == false) || title != null){
                offerRepo.searchOffer(this@OffersFragment,title)
            }else{
                mc.runOnUiThread {
                    b.loadModal.visibility = View.GONE
                    initRV()
                }
            }
        }
    }

    fun initRV(){
        adapterJob = RVAdapterJob(this, R.layout.layout_rv_offer, listOffer, db)
        b.rvOffer.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        b.rvOffer.adapter = adapterJob
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentOffersBinding.inflate(inflater, container, false)
        return b.root
    }

}