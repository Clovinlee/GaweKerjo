package com.example.gawekerjo.view

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityHomeBinding
import com.example.gawekerjo.databinding.ActivityMyOfferBinding
import com.example.gawekerjo.model.Offer.OfferItem
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.repository.OfferRepository
import com.example.gawekerjo.repository.SkillRepository
import com.example.gawekerjo.view.adapter.RVAdapterJob
import com.example.gawekerjo.view.adapter.RVAdapterMyJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyOfferActivity : AppCompatActivity() {

    private lateinit var b: ActivityMyOfferBinding
    private lateinit var db : AppDatabase
    private val coroutine = CoroutineScope(Dispatchers.IO)
    lateinit var user : UserItem

    private var listOffer : List<OfferItem> = listOf()
    private lateinit var offerRepo : OfferRepository
    private lateinit var adapterJob : RVAdapterMyJob

    lateinit var launcherEditOffer : ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        b = ActivityMyOfferBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        user = intent.getParcelableExtra<UserItem>("userlogin")!!

        db = AppDatabase.Build(this)

        offerRepo = OfferRepository(db)
        adapterJob = RVAdapterMyJob(this, R.layout.layout_rv_offer, listOffer, user, db)


        loadData()

        launcherEditOffer = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            loadData(true)
        }

        // Actionbar Back
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }

    fun loadData(fetched : Boolean = false){
        runOnUiThread {
            b.loadModal.visibility = View.VISIBLE
        }
        coroutine.launch {
            listOffer = db.offerDao.getMyOffers(user.id)

            if(listOffer.size == 0 && fetched == false){
                offerRepo.getMyOffer(this@MyOfferActivity, user)
            }else{
                runOnUiThread {
                    b.loadModal.visibility = View.GONE
                    initRV()
                }
            }
        }
    }

    fun dialog(offer : OfferItem, user : UserItem) {
        val b=layoutInflater.inflate(R.layout.dialog_layout_detailoffer,null)
        val d= Dialog(this)
        with(d) {
            setContentView(b)
            setCancelable(true)
            val btnClose = b.findViewById<ImageButton>(R.id.btnOfferDetailClose)

            val txtTitle = b.findViewById<TextView>(R.id.txtOfferDetailTitle)
            val txtSkill = b.findViewById<TextView>(R.id.txtOfferDetailSkill)
            val txtBody = b.findViewById<TextView>(R.id.txtOfferDetailBody)
            val txtUser = b.findViewById<TextView>(R.id.txtOfferDetailUser)
            val txtLocation = b.findViewById<TextView>(R.id.txtOfferDetailUserLocation)

            val txtDivider = b.findViewById<TextView>(R.id.txtDivider)

            val btnApply = b.findViewById<Button>(R.id.btnOfferDetailApply)
            val btnDelete = b.findViewById<Button>(R.id.btnOfferDetailDelete)
            val btnEdit = b.findViewById<Button>(R.id.btnofferDetailEdit)

            btnApply.visibility = View.GONE

            txtDivider.visibility = View.INVISIBLE

            txtTitle.text = offer.title
            txtSkill.text = offer.skills
            txtBody.text = offer.body
            txtUser.text = user.name
            txtLocation.text = user.lokasi

            btnDelete.setOnClickListener {
                offerRepo.deleteOffer(this@MyOfferActivity, offer.id)
                dismiss()
            }

            btnEdit.setOnClickListener {
                val i : Intent = Intent(this@MyOfferActivity, CreateOfferActivity::class.java)
                i.putExtra("userlogin",user)
                i.putExtra("offeredit",offer)
                launcherEditOffer.launch(i)
                this.dismiss()
            }

            btnClose.setOnClickListener {
                this.dismiss()
            }

            show()
        }
    }

    fun initRV(){
        adapterJob = RVAdapterMyJob(this, R.layout.layout_rv_offer, listOffer, user, db)
        b.rvMyOffer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        b.rvMyOffer.adapter = adapterJob
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}