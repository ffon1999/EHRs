package com.example.ehrs

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.login.*
import org.json.JSONObject

class Login : AppCompatActivity() {
    val url = "https://ehr-system-project.herokuapp.com/api/auth/login"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val jsonobjusername = JSONObject()
        val sharedPreferences = getSharedPreferences("User_Info", Context.MODE_PRIVATE)
        val loginBtn = findViewById<Button>(R.id.btn_login)
        val registBtn = findViewById<Button>(R.id.btn_register)

        btn_login.setOnClickListener {
            val editor = sharedPreferences.edit()

            val username: String = username.text.toString()
            val password: String = password.text.toString()
            jsonobjusername.put("user_name_patient",username)
            jsonobjusername.put("password_patient",password)

            val que = Volley.newRequestQueue(this)

            val request = JsonObjectRequest(
                Request.Method.POST,url,jsonobjusername,
                Response.Listener { response ->
                    // Process the json
                    try {
                        val obj = response
                        //Toast.makeText(applicationContext, "Volley $response "  , Toast.LENGTH_SHORT).show()
                        val username = obj.getString("user_name_patient")
                        //val password = obj.getString("password_patient")
                        val firstname = obj.getString("name_patient")
                        val surname = obj.getString("surname_patient")
                        val phone = obj.getString("mobile_phone_number_patient")
                        val email = obj.getString("email_patient")

                        //***//
                        editor.putString("Username", username)
                        //editor.putString("Password", password)
                        editor.putBoolean("Login", true )
                        editor.putString("Firstname", firstname)
                        editor.putString("Surname", surname)
                        editor.putString("Phone", phone)
                        editor.putString("Email", email)
                        //**---**
                        editor.apply()
                        //val nameU =sharedPreferences.getString("NAME","")
                        nexttohome()
                    }catch (e:Exception){
                        Log.i("i","Exception: $e")
                    }
                }, Response.ErrorListener{
                    // Error in request
                    Log.e("E","Volley error $it")
                    /*  สร้างฟังค์ชั่น แจ้งว่า time out  และ ชื่อ */
                    //Toast.makeText(applicationContext, "Volley error $it"  , Toast.LENGTH_SHORT).show()

                })
            que.add(request)
        }

        btn_register.setOnClickListener {
            val intent = Intent(this,Register::class.java)
            startActivity(intent)
        }

    }

    fun nexttohome(){
        val intent = Intent(this,Home::class.java)
        startActivity(intent)
    }

}
