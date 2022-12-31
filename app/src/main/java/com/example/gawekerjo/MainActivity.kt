package com.example.gawekerjo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityMainBinding
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.view.ChatActivity
import com.example.gawekerjo.view.HomeActivity
import com.example.gawekerjo.view.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding
    private lateinit var db : AppDatabase
    private val coroutine = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        db = AppDatabase.Build(this)

//        var userRepo : UserRepository = UserRepository(db)
//        userRepo.loadUserData()



//        var x : CountryRepository = CountryRepository(db)
//        x.getAllCountries()

        checkRemember(this)
    }

    fun checkRemember(ctx : MainActivity){
        coroutine.launch {
            val usr : UserItem? = db.userDao.getLastUser()
            runOnUiThread {
                if(usr != null){
                    val i : Intent = Intent(ctx, HomeActivity::class.java)
                    i.putExtra("userlogin", usr)
                    startActivity(i)
                }else{
                    val i : Intent = Intent(ctx, LoginActivity::class.java)
                    startActivity(i)
                }
                ctx.finish()
            }
        }
    }
}