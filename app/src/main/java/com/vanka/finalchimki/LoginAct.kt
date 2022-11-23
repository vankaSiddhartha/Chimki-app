package com.vanka.finalchimki

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        var email = findViewById<EditText>(R.id.log_Email)
        var pass = findViewById<EditText>(R.id.log_password)
        var passretype = findViewById<EditText>(R.id.log_passwordRetype)
        val btn = findViewById<Button>(R.id.login)
        findViewById<ProgressBar>(R.id.pb).visibility = View.GONE
        btn.setOnClickListener {
            login(email, pass, passretype)
        }
    }

    private fun login(email: EditText?, pass: EditText?, passretype: EditText?) {
        var mail = email!!.text.toString().trim()
        var Pass = pass!!.text.toString().trim()
        var PassRetype = passretype!!.text.toString().trim()
        if (mail.isNotEmpty() && Pass.isNotEmpty() && PassRetype.isNotEmpty()) {

            if (Pass.equals(PassRetype)) {
                findViewById<ProgressBar>(R.id.pb).visibility = View.VISIBLE
                Firebase.auth.signInWithEmailAndPassword(mail, PassRetype).addOnSuccessListener {


                    startActivity(Intent(this,Heart ::class.java))
                    findViewById<ProgressBar>(R.id.pb).visibility = View.GONE
                }.addOnFailureListener {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "Password is not mathching retype again", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(this, "Empty fills are not allowed", Toast.LENGTH_SHORT).show()
        }
    }
}