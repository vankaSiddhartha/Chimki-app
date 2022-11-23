package com.vanka.finalchimki

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.*
import android.content.Intent
import android.graphics.Color.GREEN
import android.graphics.Color.RED
import android.hardware.camera2.params.RggbChannelVector.RED
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vanka.finalchimki.Adapters.CardView
import com.vanka.finalchimki.Adapters.PostView
import com.vanka.finalchimki.ModelClass.POSTupload
import com.vanka.finalchimki.ModelClass.UserUpload
import kotlinx.coroutines.channels.Channel

class HomeFrag:Fragment(R.layout.homefrag) {
    var channelName = "Chimki"
    var channelId = "VankaSiddhartha369"
    private lateinit var list:ArrayList<POSTupload>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        var view = inflater.inflate(R.layout.homefrag, container, false)

         var rv = view.findViewById<RecyclerView>(R.id.rv_post)
        rv.layoutManager = LinearLayoutManager(requireContext())
        var list = ArrayList<POSTupload>()
        getDataFromPost()

        view.findViewById<Button>(R.id.button).setOnClickListener {
                 startActivity(Intent(requireContext(),PostUpload::class.java))
             }


        return view
    }

    private fun getDataFromPost() {
        var uid = FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseDatabase.getInstance().getReference("Post")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    list = ArrayList<POSTupload>()

                    for (data in snapshot.children) {
                        if (snapshot.exists()) {


                            var pack = data.getValue(POSTupload::class.java)
                            list.add(pack!!)
                            code = 1

                        }


                    }





                    list.shuffle()
                    try {
                        view!!.findViewById<RecyclerView>(R.id.rv_post).adapter =
                            PostView(requireContext(), list)
                    } catch (e: Exception) {

                    }
                    try {
                        view!!.findViewById<ProgressBar>(R.id.pb_home).visibility = View.GONE
                    } catch (e: Exception) {

                    }
                    try {
                        view!!.findViewById<RecyclerView>(R.id.rv_post).adapter!!.notifyDataSetChanged()
                    }catch (e:Exception){

                    }


                }

                override fun onCancelled(error: DatabaseError) {

                }




})
    }


}
