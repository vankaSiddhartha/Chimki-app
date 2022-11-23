package com.vanka.finalchimki

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

var code = 0
class Heart : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heart)
        supportActionBar?.hide()
        var homeFrag = HomeFrag()
        var chatFrag = ChatFrag()
        var profile = Profile()
        setFragmentForMain(homeFrag)
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {


                    setFragmentForMain(homeFrag)



                }




                R.id.swipe->{

                           //if(code==1)
                            setFragmentForMain(chatFrag)





                }







                R.id.chat  -> {
                    //if(code==1)
                    setFragmentForMain(profile)

                }
            }
            true

        }


    }

    private fun setFragmentForMain(fragment: Fragment) {

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.cont, fragment).setTransition(TRANSIT_FRAGMENT_OPEN)
            commit()
        }

    }


}
