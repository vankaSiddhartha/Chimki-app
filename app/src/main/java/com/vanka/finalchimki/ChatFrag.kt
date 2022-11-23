package com.vanka.finalchimki

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vanka.finalchimki.ModelClass.UserUpload

class ChatFrag:Fragment(R.layout.chat) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.chat, container, false)


         view.findViewById<Button>(R.id.male_btn).setOnClickListener {
             var intent = Intent(requireContext(),Swipe_Act::class.java)
             var sex = "male"
             intent.putExtra("sex",sex)
             startActivity(intent)

         }
        view.findViewById<Button>(R.id.fem_btn).setOnClickListener {
            var intent = Intent(requireContext(),Swipe_Act::class.java)
            var sex = "female"
            intent.putExtra("sex",sex)
            startActivity(intent)
        }
        view.findViewById<Button>(R.id.anyone).setOnClickListener {
            var intent = Intent(requireContext(),Swipe_Act::class.java)
            var sex = "female"
            intent.putExtra("sex",sex)
            startActivity(intent)
        }
        return view
    }
}