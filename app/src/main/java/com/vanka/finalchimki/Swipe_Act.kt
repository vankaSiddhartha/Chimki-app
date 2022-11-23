package com.vanka.finalchimki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vanka.finalchimki.Adapters.CardView
import com.vanka.finalchimki.ModelClass.UserUpload

class Swipe_Act : AppCompatActivity() {
    private lateinit var list: ArrayList<UserUpload>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_swipe)
        var list = ArrayList<UserUpload>()
        var sex = intent.getStringExtra("sex")
        Toast.makeText(this, "${sex}", Toast.LENGTH_SHORT).show()
       if (sex.equals("male"))
        getData(sex)
        else
           getData1()
//        else
//            getData2()

    }

    private fun getData(sex: String?) {
        var uid = FirebaseAuth.getInstance().currentUser!!.uid
       FirebaseDatabase.getInstance().getReference("Male").child("users")
           .addValueEventListener(object :ValueEventListener{
               override fun onDataChange(snapshot: DataSnapshot) {
                   list = ArrayList<UserUpload>()

                      for(data in snapshot.children){


                                  var pack = data.getValue(UserUpload::class.java)
                                  if (FirebaseAuth.getInstance().currentUser!!.uid!=pack!!.Suid)
                                      list.add(pack!!)








                  }
                   list.shuffle()
                   findViewById<ViewPager2>(R.id.viewPage).adapter = CardView(this@Swipe_Act,list)
               }

               override fun onCancelled(error: DatabaseError) {
                   TODO("Not yet implemented")
               }

           })
    }
    private fun getData1() {
        var uid = FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseDatabase.getInstance().getReference("Female").child("users")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    list = ArrayList<UserUpload>()

                    for(data in snapshot.children){
                        if (snapshot.exists()){
                            var pack = data.getValue(UserUpload::class.java)
                            if (FirebaseAuth.getInstance().currentUser!!.uid!=pack!!.Suid) {
                                list.add(pack!!)
                            }


                        }
                    }
                    list.shuffle()
                    findViewById<ViewPager2>(R.id.viewPage).adapter = CardView(this@Swipe_Act,list)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }
    private fun getData2() {
        var uid = FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseDatabase.getInstance().getReference("Users").child("Others")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    list = ArrayList<UserUpload>()

                    for(data in snapshot.children){
                        if (snapshot.exists()){
                            var pack = data.getValue(UserUpload::class.java)
                            if (FirebaseAuth.getInstance().currentUser!!.uid!=pack!!.Suid)
                                list.add(pack!!)


                        }
                    }
                    list.shuffle()

                    findViewById<ViewPager2>(R.id.viewPage).adapter = CardView(this@Swipe_Act,list)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }
}