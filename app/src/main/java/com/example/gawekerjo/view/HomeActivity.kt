package com.example.gawekerjo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.gawekerjo.R
import com.example.gawekerjo.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var b: ActivityHomeBinding
    private lateinit var fHome: HomeFragment
    private lateinit var fFollow: FollowFragment
    private lateinit var fOffer: OffersFragment

    lateinit var launcherNewPost : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityHomeBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        fHome = HomeFragment()
        fFollow = FollowFragment()
        fOffer = OffersFragment()

        launcherNewPost = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val data = it.data
            if(data != null){
                //NEW POST
            }
        }

        b.btmNav.setOnItemSelectedListener {
            if(it.itemId == R.id.menuhome){
                swapFragment(fHome)
            }else if(it.itemId == R.id.menufriend){
                swapFragment(fFollow)
            }else if(it.itemId == R.id.menupost){
                val i : Intent = Intent(this, NewPostActivity::class.java)
                launcherNewPost.launch(i)
            }else if(it.itemId == R.id.menuoffer){
                swapFragment(fOffer)
            }

            return@setOnItemSelectedListener true
        }
        swapFragment(fHome)
    }

    fun swapFragment(f : Fragment){
        var ft : FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frHome, f)
        ft.commit()
    }
}