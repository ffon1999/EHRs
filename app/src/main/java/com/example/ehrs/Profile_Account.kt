package com.example.ehrs

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

        LogOut = findViewById(R.id.btn_logout)

        btn_logout.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(this,Login::class.java)
            )
        })


    }
}