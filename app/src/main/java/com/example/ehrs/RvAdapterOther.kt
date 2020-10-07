package com.example.ehrs

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions

class RvAdapterOther(val context: Activity, val list:ArrayList<DataOther>) : RecyclerView.Adapter<ViewHolderOther>(){

    val option = RequestOptions().centerCrop().placeholder(R.drawable.ic_launcher_background).error(android.R.drawable.ic_dialog_alert)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderOther {
        val view = LayoutInflater.from(context).inflate(R.layout.item_history_other,parent,false)
        return ViewHolderOther(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolderOther, position: Int) {
        holder.tvdate.text = list[position].date
        holder.tvtime.text = list[position].time
        holder.tvdata.text = list[position].data
    }
}

class ViewHolderOther(itemView : View) : RecyclerView.ViewHolder(itemView){
    val tvdate = itemView.findViewById<TextView>(R.id.item_listview_date)
    val tvtime = itemView.findViewById<TextView>(R.id.item_listview_time)
    val tvdata = itemView.findViewById<TextView>(R.id.history_value)

}