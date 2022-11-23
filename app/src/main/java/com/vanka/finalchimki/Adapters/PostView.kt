package com.vanka.finalchimki.Adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
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
import com.google.firebase.database.ktx.getValue
import com.vanka.finalchimki.ModelClass.POSTupload
import com.vanka.finalchimki.ModelClass.UserUpload
import com.vanka.finalchimki.R

class PostView(var context: Context,var list: ArrayList<POSTupload>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.post_list,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.pm).text = list[position].postText

        Glide.with(context).load(list[position].img).listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                holder.itemView.findViewById<ProgressBar>(R.id.pb_pl).visibility= View.GONE
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                holder.itemView.findViewById<ProgressBar>(R.id.pb_pl).visibility= View.GONE
                return false
            }
        }).into(holder.itemView.findViewById(R.id.postImg ))
        FirebaseDatabase.getInstance().getReference("Users").child("users").child(FirebaseAuth.getInstance().currentUser!!.uid).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
              var Cdata = snapshot.getValue(UserUpload::class.java)
                Glide.with(context).load(Cdata!!.img).into(holder.itemView.findViewById(R.id.img_User))
                holder.itemView.findViewById<TextView>(R.id.Puser_name).text = Cdata.name


            }

            override fun onCancelled(error: DatabaseError) {

            }

        })





    }

    override fun getItemCount(): Int {
        return list.size
    }
}