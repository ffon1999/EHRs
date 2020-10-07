package com.example.ehrs

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class RvAdapter(val context: Activity, val list:ArrayList<DataBP>) : RecyclerView.Adapter<ViewHolder>(){

    val option = RequestOptions().centerCrop().placeholder(R.drawable.ic_launcher_background).error(android.R.drawable.ic_dialog_alert)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_history_bp,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvdate.text = list[position].date
        holder.tvtime.text = list[position].time
        holder.tvsys.text = list[position].sys
        holder.tvdia.text = list[position].dia
    }
}
class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    val tvdate = itemView.findViewById<TextView>(R.id.item_listview_bp_date)
    val tvtime = itemView.findViewById<TextView>(R.id.item_listview_bp_time)
    val tvsys = itemView.findViewById<TextView>(R.id.history_value_sys)
    val tvdia = itemView.findViewById<TextView>(R.id.history_value_dia)

}