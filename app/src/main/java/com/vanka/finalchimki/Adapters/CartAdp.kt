package com.vanka.finalchimki.Adapters

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vanka.finalchimki.MessegeActivity
import com.vanka.finalchimki.ModelClass.UserUpload
import com.vanka.finalchimki.ModelClassd.FirendReq
import com.vanka.finalchimki.R

class CartAdp(var context: Context,var list:ArrayList<UserUpload>):RecyclerView.Adapter<CartAdp.ViewHolder>() {
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var name = itemView.findViewById<TextView>(R.id.user_name)
        var img = itemView.findViewById<ImageView>(R.id.user_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.user_list,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.name.text = list[position].name
       Glide.with(context).load(list[position].img).into(holder.img)
        holder.itemView.setOnClickListener {
            var intent = Intent(context, MessegeActivity::class.java)
            intent.putExtra("img",list[position].img)
            intent.putExtra("RID",list[position].Suid)
            intent.putExtra("Name",list[position].name)
            intent.putExtra("College",list[position].college)
            context.startActivity(intent)
            Toast.makeText(context, "${list[position].name}", Toast.LENGTH_SHORT).show()

        }
        var count =  0

        holder.itemView.findViewById<ImageView>(R.id.user_img).setOnClickListener {
            count++
            if (count==3) {
                var data = FirebaseDatabase.getInstance().getReference("CaratData")
                    .child(FirebaseAuth.getInstance().currentUser!!.uid)
                    .child(list[position].Suid.toString())
                var data1 = FirebaseDatabase.getInstance().getReference("CaratData")
                    .child(list[position].Suid.toString())
                    .child(FirebaseAuth.getInstance().currentUser!!.uid)
                data.removeValue()
                data1.removeValue()
            }



        }




            }
          //  FirebaseDatabase.getInstance().getReference("Users").child("Male").child()


    override fun getItemCount(): Int {
        return list.size
    }


   }



