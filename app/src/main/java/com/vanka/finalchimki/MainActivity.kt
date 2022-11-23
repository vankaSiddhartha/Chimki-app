package com.vanka.finalchimki

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        Handler(Looper.myLooper()!!).postDelayed({


            if(FirebaseAuth.getInstance().currentUser!=null){
                startActivity(Intent(this,Heart::class.java))
                finish()
            }else{

                startActivity(Intent(this, WelcomePage1::class.java))
                finish()
            }


        },2000)
    }
}