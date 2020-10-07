package com.example.ehrs

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.log_history_temp.*
import kotlinx.android.synthetic.main.log_history_weight.*

class HistoryWeight : AppCompatActivity() {
    val url = "https://ehr-system-project.herokuapp.com/api/examination/list/weight/"
    private val list = ArrayList<DataOther>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_history_weight)

        callList()
    }


    fun callList(){
        val sharedPreferences = getSharedPreferences("User_Info", Context.MODE_PRIVATE)
        val username =sharedPreferences.getString("Username","Username")

        val request = JsonArrayRequest(
            Request.Method.GET,url + "$username",null,
            Response.Listener {
                for(i in 0 until it.length()){
                    val jsonObj = it.getJSONObject(i)

                    val date = jsonObj.getString("date_add")
                    val time = jsonObj.getString("time_add")
                    val datahis = jsonObj.getString("weight_patient")

                    val data = DataOther(date,time,datahis)
                    list.add(data)
                    //create adapter
                    history_weight.setHasFixedSize(true)
                    history_weight.layoutManager = LinearLayoutManager(this)
                    val adapter = RvAdapterOther(this,list)
                    history_weight.adapter = adapter

                }
            },
            Response.ErrorListener { Toast.makeText(this, "Something went wrong!! $it", Toast.LENGTH_SHORT).show()
                Log.e("eeee","$it")
            })

        val queue = Volley.newRequestQueue(this)
        queue.add(request)


    }

}