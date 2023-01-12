package com.example.gawekerjo.view

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityHomeBinding
import com.example.gawekerjo.env
import com.example.gawekerjo.model.follow.Follow
import com.example.gawekerjo.model.follow.FollowItem
import com.example.gawekerjo.model.postlike.postLike
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.repository.CountryRepository
import com.example.gawekerjo.repository.EducationRepository
import com.example.gawekerjo.repository.FollowRepository
import com.example.gawekerjo.repository.SkillRepository
import com.example.gawekerjo.view.adapter.FollowAdapter
import com.example.gawekerjo.view.adapter.FollowAdapter2
import com.google.android.material.navigation.NavigationView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL


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
    private lateinit var skillrepo : SkillRepository
    private lateinit var edurepo: EducationRepository

//    INI PUNYA ESTHER YG MASIH BLM BISA JALAN
    private lateinit var FollowAdapter2 : FollowAdapter2
    private lateinit var accFollow : FollowRepository

    private lateinit var followList :ArrayList<FollowItem>
// SAMPE SINI PNY ESTHER
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityHomeBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)
        db = AppDatabase.Build(this)
        //        INI JUGA ESTHER
        followList = ArrayList()
        accFollow = FollowRepository(db)


        // GET USER FROM PARCELABLE, if null exit
        try {
            user = intent.getParcelableExtra<UserItem>("userlogin")!!
        }catch (th : Throwable){
            Log.d("CCD","Error, user not found!")
            Log.d("CCD",th.message.toString())
            finish()
        }

        //        INI JUGA ESTHER
        accFollow.getFriends2(this,null,user.id,null)
        Log.d("CCD", "Ini nyoba di homeactivity size e : " + followList.size.toString())
        //untuk profil
        skillrepo = SkillRepository(db)
        edurepo = EducationRepository(db)
        skillrepo.getAllSkill(this)
//        skillrepo.getUserSkill(this, user.id, null)
//        edurepo.getUserEdu(this, null, user.id)

    coroutine.launch {
        db.userskillDao.clear()
        db.userlanguageDao.clear()
        db.educationDao.clear()
    }


        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // CHANGE ICON
//        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_home_24, this.getTheme())
//        supportActionBar?.setHomeAsUpIndicator(resize(resources.getDrawable(R.drawable.anon),90,90))
        // CHANGE ICON

        navView.setNavigationItemSelectedListener {

            if(it.itemId == R.id.navmenu_home){
                val mhome = b.btmNav.findViewById<View>(R.id.menuhome)
                mhome.performClick()
                drawerLayout.closeDrawer(Gravity.LEFT)
            }else if(it.itemId == R.id.navmenu_profile){
                // ACTIVITY TO PROFILE
//                Toast.makeText(this, "${user.id}", Toast.LENGTH_SHORT).show()
                runOnUiThread {
                    if (user.type == "1"){

                        val i : Intent = Intent(this, UserprofileActivity::class.java)
                        i.putExtra("userLogin", user)
                        startActivity(i)
                    }
                    else{
                        val i : Intent = Intent(this, CompanyProfileActivity::class.java)
                        i.putExtra("userLogin", user)
                        startActivity(i)
                    }
                }
            }else if(it.itemId == R.id.navmenu_messages){
                // ACTIVITY TO MESSAGES
                val i=Intent(this,ChatActivity::class.java)
                i.putExtra("userlogin",user)
                startActivity(i)
            }else if(it.itemId == R.id.navmenu_friend){
                // ACTIVITY TO FRIEND
                // JANGAN LUPA PASSING PARCELABLE USER KE ACTIVITY (opsional buat ambil user)
                // Gausah di finish(), jadi kalo user mencet back, kembali ke menu ini
                // SLOT PUNYA ( ESTHER GABRIEL TRIVENA )
                val i=Intent(this,FriendListActivity::class.java)
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
            }else if(it.itemId == R.id.menuaddjob){
                val i : Intent = Intent(this, CreateOfferActivity::class.java)
                i.putExtra("userlogin", user)
                startActivity(i)

            }else if(it.itemId == R.id.menufindjob){
                val mhome = b.btmNav.findViewById<View>(R.id.menuoffer)
                mhome.performClick()
                drawerLayout.closeDrawer(Gravity.LEFT)
            }else if(it.itemId == R.id.menumyjob){
                val i : Intent = Intent(this, MyOfferActivity::class.java)
                i.putExtra("userlogin",user)
                startActivity(i)
            }

            return@setNavigationItemSelectedListener true
        }

        // DYNAMIC DRAWER HEADER

        var navigationView : NavigationView = findViewById(R.id.nav_view);
        var headerview = navigationView.getHeaderView(0)

        var circleAvatar : CircleImageView = headerview.findViewById(R.id.circleAvatar)
        var txtName : TextView = headerview.findViewById(R.id.txtUserDrawerName)
        var txtEmail : TextView = headerview.findViewById(R.id.txtUserDrawerEmail)

        coroutine.launch {
            if (user.image!=null){
                val i= URL(env.API_URL.substringBefore("/api/")+user.image).openStream()
                val image= BitmapFactory.decodeStream(i)
                runOnUiThread { circleAvatar.setImageBitmap(image) }
            }
        }

        txtName.text = user!!.name
        txtEmail.text = user!!.email
        circleAvatar.setOnClickListener {
            // ACTIVITY TO PROFILE
            // JANGAN LUPA PASSING PARCELABLE USER KE ACTIVITY (opsional buat ambil user)
            // Gausah di finish(), jadi kalo user mencet back, kembali ke menu ini
            runOnUiThread {
//                val i : Intent = Intent(this, UserprofileActivity::class.java)
//                i.putExtra("userLogin", user)
//                startActivity(i)

                if (user.type == "1"){

                    val i : Intent = Intent(this, UserprofileActivity::class.java)
                    i.putExtra("userLogin", user)
                    startActivity(i)
                }
                else{
                    val c : Intent = Intent(this, CompanyProfileActivity::class.java)
                    c.putExtra("userLogin", user)
                    startActivity(c)
                }
            }
        }

        // END OF DYNAMIC DRAWER HEADER

        fHome = HomeFragment(this, db, user)
//        fFollow = FollowFragment()
        fOffer = OffersFragment(this, db, user)

        launcherNewPost = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val data = it.data
            if(data != null){
                fHome.loadDataPost()
            }
        }

        b.btmNav.setOnItemSelectedListener {
            if(it.itemId == R.id.menuhome){
                swapFragment(fHome)
            }else if(it.itemId == R.id.menupost){
                val i : Intent = Intent(this, NewPostActivity::class.java)
                i.putExtra("userlogin", user)
//                startActivity(i)
                launcherNewPost.launch(i)
            }else if(it.itemId == R.id.menuoffer){
                swapFragment(fOffer)
            }

            return@setOnItemSelectedListener true
        }
        swapFragment(fHome)
    }

//  INI JUGA ESTHER PUNYA
    fun refresh(result : Follow){
        followList = result.data as ArrayList<FollowItem>
        Log.d("CCD", "Ini nyoba di refresh size e : " + followList.size.toString())
        FollowAdapter2 = FollowAdapter2(followList,this,user)
        FollowAdapter2.notifyDataSetChanged()
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

    //UNTUK PROSES LIKE
    fun addPostLikeCallBack(result : postLike){
        if(result.status == 200){
            Toast.makeText(this, "${result.message}", Toast.LENGTH_SHORT).show()
            val i = Intent()
            setResult(1, intent)
            finish()
        }
        else{
            Toast.makeText(this, "${result.message}", Toast.LENGTH_SHORT).show()
        }
    }
}