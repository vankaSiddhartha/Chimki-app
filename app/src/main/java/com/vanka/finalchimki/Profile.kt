package com.vanka.finalchimki

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vanka.finalchimki.Adapters.CartAdp
import com.vanka.finalchimki.ModelClass.MessegeModel
import com.vanka.finalchimki.ModelClass.POSTupload
import com.vanka.finalchimki.ModelClass.UserUpload
import com.vanka.finalchimki.ModelClassd.FirendReq

class Profile:Fragment(R.layout.profile) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        var view = inflater.inflate(R.layout.profile, container, false)
        view.findViewById<RecyclerView>(R.id.rv).layoutManager = LinearLayoutManager(requireContext())
        ShowFriends()



        return view
    }

    private fun ShowFriends() {
        var uid = FirebaseAuth.getInstance().currentUser!!.uid
        Toast.makeText(requireContext(), "${uid}", Toast.LENGTH_SHORT).show()
        FirebaseDatabase.getInstance().getReference("CaratData").child(uid)
           .addValueEventListener(object : ValueEventListener{
               override fun onDataChange(snapshot: DataSnapshot) {
                   var cuid = FirebaseAuth.getInstance().currentUser!!.uid
                   var list = ArrayList<UserUpload>()
                 if (snapshot.exists()){
                     for (data in snapshot.children){
                         try {

                             var getData = data.getValue(UserUpload::class.java)

                                  list.add(getData!!)

                         }catch (e:Exception){

                         }


                     }
                 }

                     try {
                         view!!.findViewById<RecyclerView>(R.id.rv).adapter = CartAdp(requireContext(),list)
                     }catch (e:Exception){

                     }

               }

               override fun onCancelled(error: DatabaseError) {
                   TODO("Not yet implemented")
               }

           })
    }
}