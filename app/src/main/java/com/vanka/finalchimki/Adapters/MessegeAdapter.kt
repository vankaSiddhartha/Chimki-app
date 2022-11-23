package com.vanka.finalchimki.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.vanka.finalchimki.ModelClass.MessegeModel
import com.vanka.finalchimki.R

class MessegeAdapter(var context: Context,var list:ArrayList<MessegeModel>):RecyclerView.Adapter<MessegeAdapter.ViewHolder>() {
    var reciveR = 0;
    var sendL = 1;
    override fun getItemViewType(position: Int): Int {
        if (list[position].SID!= FirebaseAuth.getInstance().currentUser!!.uid){
            return reciveR
        }else{
            return  sendL
        }
    }
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var mess = itemView.findViewById<TextView>(R.id.Text )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(viewType==reciveR){
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recive_item,parent,false))
        }else{
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.sent,parent,false))
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mess.text= list[position].message!!
        holder.itemView.findViewById<TextView>(R.id.Text).setOnClickListener {
            var rdf = FirebaseDatabase.getInstance().getReference("Chats").child(FirebaseAuth.getInstance().currentUser!!.uid)
            rdf.removeValue()
        }
    }

    override fun getItemCount(): Int {
      return list.size
    }

}