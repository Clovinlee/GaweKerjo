package com.example.gawekerjo.view

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityHomeBinding
import com.example.gawekerjo.model.user.UserItem
import com.google.android.material.navigation.NavigationView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeActivity : AppCompatActivity() {

    private lateinit var b: ActivityHomeBinding
    private lateinit var fHome: HomeFragment
    private lateinit var fFollow: FollowFragment
    private lateinit var fOffer: OffersFragment

    private lateinit var db : AppDatabase
    private val coroutine = CoroutineScope(Dispatchers.IO)

    lateinit var launcherNewPost : ActivityResultLauncher<Intent>

    lateinit var toggle : ActionBarDrawerToggle

    lateinit var user : UserItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityHomeBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)
        db = AppDatabase.Build(this)

        // GET USER FROM PARCELABLE, if null exit
        try {
            user = intent.getParcelableExtra<UserItem>("userlogin")!!
        }catch (th : Throwable){
            Log.d("CCD","Error, user not found!")
            Log.d("CCD",th.message.toString())
            finish()
        }

        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // CHANGE ICON
        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_home_24, this.getTheme())
        supportActionBar?.setHomeAsUpIndicator(resize(resources.getDrawable(R.drawable.anon),90,90))
        // CHANGE ICON

        navView.setNavigationItemSelectedListener {

            if(it.itemId == R.id.navmenu_home){
                val mhome = b.btmNav.findViewById<View>(R.id.menuhome)
                mhome.performClick()
                drawerLayout.closeDrawer(Gravity.LEFT)
            }else if(it.itemId == R.id.navmenu_profile){
                // ACTIVITY TO PROFILE
                // JANGAN LUPA PASSING PARCELABLE USER KE ACTIVITY (opsional buat ambil user)
                // Gausah di finish(), jadi kalo user mencet back, kembali ke menu ini
//                Toast.makeText(this, "${user.id}", Toast.LENGTH_SHORT).show()
                runOnUiThread {
                    val i : Intent = Intent(this, UserprofileActivity::class.java)
                    i.putExtra("userLogin", user)
                    startActivity(i)
                }
            }else if(it.itemId == R.id.navmenu_messages){
                // ACTIVITY TO PROFILE
                // JANGAN LUPA PASSING PARCELABLE USER KE ACTIVITY (opsional buat ambil user)
                // Gausah di finish(), jadi kalo user mencet back, kembali ke menu ini
                val i=Intent(this,ChatActivity::class.java)
                i.putExtra("userlogin",user)
                startActivity(i)
            }else if(it.itemId == R.id.navmenu_logout){
                coroutine.launch {
                    db.userDao.clear()
                    runOnUiThread {
                        val i : Intent = Intent(this@HomeActivity, LoginActivity::class.java)
                        startActivity(i)
                        finish()
                    }
                }
            }

            return@setNavigationItemSelectedListener true
        }

        // DYNAMIC DRAWER HEADER

        var navigationView : NavigationView = findViewById(R.id.nav_view);
        var headerview = navigationView.getHeaderView(0)

        var circleAvatar : CircleImageView = headerview.findViewById(R.id.circleAvatar)
        var txtName : TextView = headerview.findViewById(R.id.txtUserDrawerName)
        var txtEmail : TextView = headerview.findViewById(R.id.txtUserDrawerEmail)

        txtName.text = user!!.name
        txtEmail.text = user!!.email
        circleAvatar.setOnClickListener {
            // ACTIVITY TO PROFILE
            // JANGAN LUPA PASSING PARCELABLE USER KE ACTIVITY (opsional buat ambil user)
            // Gausah di finish(), jadi kalo user mencet back, kembali ke menu ini
        }

        // END OF DYNAMIC DRAWER HEADER

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

    private fun resize(image: Drawable, w : Int ,h : Int): Drawable? {
        val b = (image as BitmapDrawable).bitmap
        val bitmapResized = Bitmap.createScaledBitmap(b, w, h, false)
        return BitmapDrawable(resources, bitmapResized)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){

        }
        return true
    }

    fun swapFragment(f : Fragment){
        var ft : FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frHome, f)
        ft.commit()
    }
}