package com.vanka.finalchimki

import android.app.*
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.vanka.finalchimki.Adapters.MessegeAdapter
import com.vanka.finalchimki.ModelClass.MessegeModel
import com.vanka.finalchimki.ModelClass.UserUpload
import java.util.*
import kotlin.collections.ArrayList
var list1 = ArrayList<String>()
class MessegeActivity : AppCompatActivity() {

    var q =0
    var channelId = "Id"
    var Id = 0
    var channelName = "Name"
    private lateinit var list:ArrayList<MessegeModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messege)
        supportActionBar?.hide()
        var RID = intent.getStringExtra("RID")
        var img = intent.getStringExtra("img")
        var name = intent.getStringExtra("Name")
        var college = intent.getStringExtra("College")
        CartData(RID,img,name,college)








        var SID = FirebaseAuth.getInstance().currentUser!!.uid
        var messege = findViewById<EditText>(R.id.messege)
        var ROOMID = SID+RID
        MessegeNotification(ROOMID)
        var rv = findViewById<RecyclerView>(R.id.rv_m)
        rv.layoutManager = LinearLayoutManager(this)
        list = ArrayList<MessegeModel>()
        GETT(ROOMID)
//        list.add(Model("wsgjwswws","yufw",FirebaseAuth.getInstance().currentUser!!.uid,"hai"))
//        list.add(Model("swj","sws","wssw","Hello"))

      findViewById<Button>(R.id.sendbtn).setOnClickListener {
          getData(ROOMID, RID, SID, messege)


      }


    }

    private fun MessegeNotification(ROOMID: String) {
        var uid = FirebaseAuth.getInstance().currentUser!!.uid.toString()
        var rdef = FirebaseDatabase.getInstance().getReference().child("Chats").child(ROOMID)
        rdef.addChildEventListener(object :ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

               // createNotification()

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun createNotification() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelName,channelId,NotificationManager.IMPORTANCE_HIGH).apply {
                lightColor = Color.BLACK
                enableLights(true)
            }
            var manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
            val intent = Intent(this,Heart::class.java)
            var paddingIntent = PendingIntent.getActivity(
                this,
                1,
                intent,
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N) PendingIntent.FLAG_IMMUTABLE else 0
            )

            val noti = NotificationCompat.Builder(this ,channelName)
                .setContentTitle("Messege lu vachayi")
                .setContentText("ayya messege lu vachayi")
                .setSmallIcon(R.drawable.notification)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(paddingIntent)
                .build()
            val notifyManager = NotificationManagerCompat.from(this)
            notifyManager.notify(Id,noti)
        }
    }

    private fun CartData(rid: String?, img: String?, name: String?, college: String?) {

      FirebaseDatabase.getInstance().getReference("Users").child("users").child(FirebaseAuth.getInstance().currentUser!!.uid)
          .addListenerForSingleValueEvent(object : ValueEventListener{
              override fun onDataChange(snapshot: DataSnapshot) {
                  q++
                  var data = snapshot.getValue(UserUpload::class.java)


                  var data1 = UserUpload(name,college,rid,img,"0")

                  var uid = FirebaseAuth.getInstance().currentUser!!.uid
                  FirebaseDatabase.getInstance().getReference("CaratData").child(uid!!).child(rid!!).setValue(data1).addOnSuccessListener {
                      Toast.makeText(this@MessegeActivity, "Hola", Toast.LENGTH_SHORT).show()
                      FirebaseDatabase.getInstance().getReference("CaratData").child(rid!!).child(uid).setValue(data).addOnSuccessListener {
                          Toast.makeText(this@MessegeActivity, "Hola", Toast.LENGTH_SHORT).show()

                      }


                  }



              }

              override fun onCancelled(error: DatabaseError) {

              }

          })
    }

    private fun GETT(ROOMID: String) {
        FirebaseDatabase.getInstance().getReference("Chats").child(ROOMID)
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    list = ArrayList<MessegeModel>()

                    for (data in snapshot.children){
                        if(snapshot.exists()){
                            q++;
                            var getInfo = data.getValue(MessegeModel::class.java)

                            list.add(getInfo!!)


                        }
                        findViewById<EditText>(R.id.messege).setText("")

                    }

                    findViewById<RecyclerView>(R.id.rv_m).adapter = MessegeAdapter(this@MessegeActivity,list)

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }


    private fun getData(roomid: String, rid: String?, sid: String, messege: EditText?) {
        var mess = messege!!.text.toString()

        var data = MessegeModel(sid,mess)
        var key =  UUID.randomUUID().toString()
        var revRoom = rid+sid



        FirebaseDatabase.getInstance().reference.child("Chats").child(roomid).push().setValue(data).addOnSuccessListener {

            FirebaseDatabase.getInstance().reference.child("Chats").child(revRoom).push().setValue(data)
        }


    }
    }
