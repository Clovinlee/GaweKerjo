package com.example.gawekerjo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        var user : UserItem? = intent.getParcelableExtra<UserItem>("userlogin")

        if(user == null){
            Toast.makeText(this, "Error, invalid user!", Toast.LENGTH_SHORT).show()
            finish()
        }

        offerRepo = OfferRepository(db)

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

}