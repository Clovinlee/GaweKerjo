package com.example.gawekerjo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityCreateOfferBinding
import com.example.gawekerjo.databinding.ActivityHomeBinding
import com.example.gawekerjo.model.Offer.Offer
import com.example.gawekerjo.model.Offer.OfferItem
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.repository.OfferRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateOfferActivity : AppCompatActivity() {

    private lateinit var b: ActivityCreateOfferBinding
    private lateinit var db : AppDatabase
    private lateinit var offerRepo : OfferRepository

    private val coroutine = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityCreateOfferBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        db = AppDatabase.Build(this)
        var user : UserItem? = intent.getParcelableExtra("userlogin")
        var offer : OfferItem? = intent.getParcelableExtra("offeredit")

        if(user == null){
            Toast.makeText(this, "Error, invalid user!", Toast.LENGTH_SHORT).show()
            finish()
        }

        offerRepo = OfferRepository(db)
        b.btnEditOffer.visibility = View.GONE

        if(offer != null){
            b.btnCreateOffer.visibility = View.GONE
            b.btnEditOffer.visibility = View.VISIBLE

            b.txtCreateOfferTitle.setText(offer.title)
            b.txtCreateOfferSkills.setText(offer.skills)
            b.txtCreateOfferDescription.setText(offer.body)
        }

        b.btnEditOffer.setOnClickListener {
            val title = b.txtCreateOfferTitle.text.toString()
            val skills = b.txtCreateOfferSkills.text.toString()
            val body = b.txtCreateOfferDescription.text.toString()

            if(title == ""){
                Toast.makeText(this, "Error, title cannot be empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(skills == ""){
                Toast.makeText(this, "Error, skills cannot be empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            offer!!.title = title
            offer!!.skills = skills
            offer!!.body = body

            offerRepo.editOffer(this, offer)
        }

        b.btnCreateOffer.setOnClickListener {
            val title = b.txtCreateOfferTitle.text.toString()
            val skills = b.txtCreateOfferSkills.text.toString()
            val body = b.txtCreateOfferDescription.text.toString()

            if(title == ""){
                Toast.makeText(this, "Error, title cannot be empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(skills == ""){
                Toast.makeText(this, "Error, skills cannot be empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val offer = OfferItem(99, user!!.id, title, body, skills, null, null)
            offerRepo.addOffer(this, offer)
        }

        b.btnCreateOfferBack.setOnClickListener {
            finish()
        }

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }

    fun editCallback(){
        runOnUiThread {
            Toast.makeText(this, "Sukses edit job offer!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun repoCallback(offer : Offer){
        Toast.makeText(this, "Sukses add new job offer!", Toast.LENGTH_SHORT).show()
        coroutine.launch {
            db.offerDao.clear()
            runOnUiThread {
                finish()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}