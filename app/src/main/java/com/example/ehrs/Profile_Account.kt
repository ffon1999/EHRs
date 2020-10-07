package com.example.ehrs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.home.*
import kotlinx.android.synthetic.main.profile_user.*

class Profile_Account : AppCompatActivity() {

    var LogOut : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_user)

        val sharedPreferences = getSharedPreferences("User_Info", Context.MODE_PRIVATE)
        val loginapp =sharedPreferences.getBoolean("Login",false)

            if (loginapp==false){
                startActivity(
                    Intent(this,Login::class.java)
                )
            }


        val username =sharedPreferences.getString("Username","...")
        val firstname =sharedPreferences.getString("Firstname","...")
        val surname =sharedPreferences.getString("Surname","...")
        val phone =sharedPreferences.getString("Phone","...")
        val email =sharedPreferences.getString("Email","...")

        /* Set data to text  */
        username_profile.text = "$username"
        firstname_profile.text = "$firstname"
        surname_profile.text = "$surname"
        phone_profile.text = "$phone"
        email_profile.text = "$email"


        LogOut = findViewById(R.id.btn_logout)

        btn_logout.setOnClickListener(View.OnClickListener {


            val editor = sharedPreferences.edit()
            //***//
            editor.putString("Username", "")
            //editor.putString("Password", password)
            editor.putBoolean("Login", false )
            editor.putString("Firstname", "")
            editor.putString("Surname", "")
            editor.putString("Phone", "")
            editor.putString("Email", "")
            //**---**
            editor.apply()


            startActivity(
                Intent(this,Login::class.java)
            )
        })


    }
}