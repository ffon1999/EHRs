package com.example.ehrs

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.input_mn_pr.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AddPR  : AppCompatActivity() {
    val url = "https://ehr-system-project.herokuapp.com/api/examination"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_mn_pr)


        update_mn_pr.setOnClickListener {

            val heartrate: Int = input_value_pr.text.toString().toInt()

            /* set sharedPreferences */
            val sharedPreferences = getSharedPreferences("User_Info", Context.MODE_PRIVATE)
//            /* set editor  */
//            val editor = sharedPreferences.edit()
//            editor.putFloat("Bloodsys", bloodsys.toFloat())
//            editor.putFloat("Blooddia", blooddia.toFloat())
//            editor.apply()

            /* get name from data sharedPreferences */
            val username =sharedPreferences.getString("Username","")

            /*   set Date  */
            var answerdate1 : String = ""
            var answertime2 : String = ""
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss")
                val formatterdate = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                val formattertime = DateTimeFormatter.ofPattern("HH:mm:ss")

                //"dd.MM.yyyy. HH:mm:ss"
                var answer: String =  current.format(formatter)
                val answerdate: String =  current.format(formatterdate)
                val answertime: String =  current.format(formattertime)
                answerdate1 = answerdate
                answertime2 = answertime
                //Log.d("answer",answer)
            } else {
                var date = Date()
                val formatter = SimpleDateFormat("MMM dd yyyy HH:mma")
                val answer: String = formatter.format(date)
                Log.d("answer",answer)
            }

            /*   set Date to JSONObject */
            val jsonobjAddBP = JSONObject()
            jsonobjAddBP.put("user_name_patient",username)
            jsonobjAddBP.put("heart_rate_patient",heartrate)
            jsonobjAddBP.put("date_add",answerdate1)
            jsonobjAddBP.put("time_add",answertime2)

            /*    */
            val que = Volley.newRequestQueue(this)
            val request = JsonObjectRequest(
                Request.Method.POST,url,jsonobjAddBP,
                Response.Listener { response ->
                    // Process the json

                    try {
                        val obj = response

                        /* set editor  */
                        val editor = sharedPreferences.edit()
                        editor.putInt("NumberPR", heartrate)
                        editor.apply()

                        //textView10.text = "Response: $response"
                        //Toast.makeText(applicationContext, "Volley $response "  , Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@AddPR, Home::class.java)
                        startActivity(intent)

                    }catch (e:Exception){
                        //textView10.text = "Exception: $e "
                    }

                }, Response.ErrorListener{
                    // Error in request
                    // textView10.text = "Volley error: $it "
                    Toast.makeText(applicationContext, "Volley error $it"  , Toast.LENGTH_SHORT).show()

                })
            que.add(request)

        }


    }
}