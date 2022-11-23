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
import com.google.firebase.database.FirebaseDatabase
import com.vanka.finalchimki.MessegeActivity
import com.vanka.finalchimki.ModelClass.UserUpload
import com.vanka.finalchimki.ModelClassd.FirendReq
import com.vanka.finalchimki.R

class CardView(var context: Context,var list:ArrayList<UserUpload>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
   inner class ViewHolder(itemview:View):RecyclerView.ViewHolder(itemview){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
      var view = LayoutInflater.from(parent.context).inflate(R.layout.swipe_list,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
      holder.itemView.findViewById<TextView>(R.id.c_name).text = list[position].name
        holder.itemView.findViewById<TextView>(R.id.c_college).text = list[position].college
        Glide.with(context).load(list[position].img).listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
               holder.itemView.findViewById<ProgressBar>(R.id.pb_sw).visibility = View.GONE
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                holder.itemView.findViewById<ProgressBar>(R.id.pb_sw).visibility = View.GONE
                return false
            }

        }).into(holder.itemView.findViewById(R.id.c_img))
        holder.itemView.findViewById<Button>(R.id.addFriend).setOnClickListener {
         FirebaseDatabase.getInstance().getReference("CaratData")
        }
        holder.itemView.findViewById<ImageView>(R.id.messegebtn).setOnClickListener {
           var intent = Intent(context,MessegeActivity::class.java)
            intent.putExtra("img",list[position].img)
            intent.putExtra("RID",list[position].Suid)
            intent.putExtra("Name",list[position].name)
            intent.putExtra("College",list[position].college)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return  list.size
    }


}