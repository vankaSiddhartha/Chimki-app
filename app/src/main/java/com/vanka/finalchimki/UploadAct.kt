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
import com.vanka.finalchimki.ModelClass.UserUpload

class UploadAct : AppCompatActivity() {
     var sex = 0
    private var imageUri: Uri? = null
    private lateinit var firebase: FirebaseDatabase
    private var selectedImg = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it
        findViewById<ImageView>(R.id.profile).setImageURI(imageUri)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        supportActionBar?.hide()
        
        findViewById<ImageView>(R.id.profile).setOnClickListener {
            selectedImg.launch("image/*")
        }

        var college = findViewById<EditText>(R.id.college)
        var name = findViewById<EditText>(R.id.name)
        findViewById<ProgressBar>(R.id.pb_ca).visibility = View.GONE

        var female = findViewById<CheckBox>(R.id.female)
        var male = findViewById<CheckBox>(R.id.male)
        var other = findViewById<CheckBox>(R.id.others)
        findViewById<Button>(R.id.next).setOnClickListener {
            findViewById<ProgressBar>(R.id.pb_ca).visibility = View.VISIBLE
            Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show()

                if(female.isChecked){
                  sex = 1
                }else if(male.isChecked){
                    Toast.makeText(this, "male", Toast.LENGTH_SHORT).show()
                    sex =2
                }else{
                   sex =3 
                }

                imgUpload(college, name,sex)

        }
    }

    private fun imgUpload(college: EditText, name: EditText, sex: Int) {
        var putImg = FirebaseStorage.getInstance().getReference("profile")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child("profile.jpg")

        if (imageUri != null) {
            putImg.putFile(imageUri!!)
                .addOnSuccessListener {
                    putImg.downloadUrl.addOnSuccessListener {

                        Upload(college, name, it,sex)

                    }.addOnFailureListener {
                        Toast.makeText(this, "noo", Toast.LENGTH_SHORT).show()
                    }


                }
        }else{
            Toast.makeText(this, "choose your profile!!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun Upload(college: EditText, name: EditText, it: Uri?, i: Int) {
        var nam = name.text.toString()
        var col = college.text.toString()
        var uid = FirebaseAuth.getInstance().currentUser!!.uid
        if (i==1){
            Upload1(college,name,it)
        }else{
            Upload2(college,name,it)
        }
        if(nam.isNotEmpty()&&col.isNotEmpty()) {
            var data = UserUpload(
                nam,
                col,
                uid,
                it.toString()
            )

            FirebaseDatabase.getInstance().getReference("Users").child("users").child(uid).setValue(data)
                .addOnSuccessListener {

                    findViewById<ProgressBar>(R.id.pb_ca).visibility = View.GONE
                    startActivity(Intent(this,Heart::class.java))
                }.addOnFailureListener {
                    Toast.makeText(this, "sss", Toast.LENGTH_SHORT).show()
                }
        }else{
            Toast.makeText(this, "Fill the blanks!!!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun Upload1(college: EditText, name: EditText, it: Uri?) {
           var nam = name.text.toString()
           var col = college.text.toString()
        var uid = FirebaseAuth.getInstance().currentUser!!.uid
        if(nam.isNotEmpty()&&col.isNotEmpty()) {
            var data = UserUpload(
                nam,
                col,
                uid,
                it.toString(),
                "2"
            )

            FirebaseDatabase.getInstance().reference.child("Female").child("users").child(uid).setValue(data)
                .addOnSuccessListener {
                    findViewById<ProgressBar>(R.id.pb_ca).visibility = View.GONE
                           startActivity(Intent(this,Heart::class.java))
                }.addOnFailureListener {
                    Toast.makeText(this, "sss", Toast.LENGTH_SHORT).show()
                }
        }else{
            Toast.makeText(this, "Fill the blanks!!!", Toast.LENGTH_SHORT).show()
        }
    }
    private fun Upload2(college: EditText, name: EditText, it: Uri?) {
        var nam = name.text.toString()
        var col = college.text.toString()
        var uid = FirebaseAuth.getInstance().currentUser!!.uid
        if(nam.isNotEmpty()&&col.isNotEmpty()) {
            var data = UserUpload(
                nam,
                col,
                uid,
                it.toString(),
                "1"
            )

            FirebaseDatabase.getInstance().reference.child("Male").child("users").child(uid).setValue(data)
                .addOnSuccessListener {
                    findViewById<ProgressBar>(R.id.pb_ca).visibility = View.GONE
                    startActivity(Intent(this,Heart::class.java))
                }.addOnFailureListener {
                    Toast.makeText(this, "sss", Toast.LENGTH_SHORT).show()
                }
        }else{
            Toast.makeText(this, "Fill the blanks!!!", Toast.LENGTH_SHORT).show()
        }
    }
    private fun Upload3(college: EditText, name: EditText, it: Uri?) {
        var nam = name.text.toString()
        var col = college.text.toString()
        var uid = FirebaseAuth.getInstance().currentUser!!.uid
        if(nam.isNotEmpty()&&col.isNotEmpty()) {
            var data = UserUpload(
                nam,
                col,
                uid,
                it.toString()
            )

            FirebaseDatabase.getInstance().getReference("Users").child("Others").child(uid).setValue(data)
                .addOnSuccessListener {
                    findViewById<ProgressBar>(R.id.pb_ca).visibility = View.GONE
                    startActivity(Intent(this,Heart::class.java))
                }.addOnFailureListener {
                    Toast.makeText(this, "sss", Toast.LENGTH_SHORT).show()
                }
        }else{
            Toast.makeText(this, "Fill the blanks!!!", Toast.LENGTH_SHORT).show()
        }
    }
}




