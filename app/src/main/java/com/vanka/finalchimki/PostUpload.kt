package com.vanka.finalchimki

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.vanka.finalchimki.ModelClass.POSTupload
import com.vanka.finalchimki.ModelClass.UserUpload
import java.util.*

class PostUpload : AppCompatActivity() {
    private var imageUri: Uri? = null
    private lateinit var firebase: FirebaseDatabase
    private var selectedImg = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it
        findViewById<ImageView>(R.id.im_post).setImageURI(imageUri)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_upload)
        findViewById<ProgressBar>(R.id.pb_up).visibility = View.GONE
        findViewById<Button>(R.id.im_upload).setOnClickListener {
            selectedImg.launch("image/*")
        }

        var name = findViewById<EditText>(R.id.p_name)
        var postText = findViewById<EditText>(R.id.post_text)
        var upload = findViewById<Button>(R.id.Pupload)

        upload.setOnClickListener {




            imgUpload(name,postText)
            findViewById<ProgressBar>(R.id.pb_up).visibility = View.VISIBLE

        }

    }
    private fun imgUpload(name: EditText, postText: EditText) {
        var uidQ =UUID.randomUUID().toString()
        var putImg = FirebaseStorage.getInstance().getReference("post")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child(uidQ)
        if (imageUri != null) {
            putImg.putFile(imageUri!!)
                .addOnSuccessListener {
                    putImg.downloadUrl.addOnSuccessListener {
                        UploadPost(name,postText,it)

                    }.addOnFailureListener {
                        Toast.makeText(this, "noo", Toast.LENGTH_SHORT).show()
                    }


                }
        }else{
            Toast.makeText(this, "choose your profile!!", Toast.LENGTH_SHORT).show()
        }
    }
    private fun UploadPost(name: EditText?, postText: EditText?, img: Uri) {
    var naam = name!!.text.toString()
        var pText = postText!!.text.toString()
        var uid = FirebaseAuth.getInstance().currentUser!!.uid
        if(naam.isNotEmpty()&&pText.isNotEmpty()){
            var data = POSTupload(
                img.toString(),
                pText,
                naam
            )
           var RUID = UUID.randomUUID().toString()
            FirebaseDatabase.getInstance().getReference("Post").child(RUID).setValue(data)
                .addOnSuccessListener {
                    findViewById<ProgressBar>(R.id.pb_up).visibility = View.GONE
                    startActivity(Intent(this,Heart::class.java))
                }.addOnFailureListener {
                    Toast.makeText(this, "sss", Toast.LENGTH_SHORT).show()
                }
        }else{
            Toast.makeText(this, "Fill the blanks!!!", Toast.LENGTH_SHORT).show()
        }
    }


}