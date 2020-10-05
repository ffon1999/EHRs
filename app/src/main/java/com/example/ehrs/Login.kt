package com.example.ehrs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.login.*

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val loginBtn = findViewById<Button>(R.id.btn_login)
        val registBtn = findViewById<Button>(R.id.btn_register)

        btn_login.setOnClickListener {
            val intent = Intent(this,Home::class.java)
            startActivity(intent)
        }

        btn_register.setOnClickListener {
            val intent = Intent(this,Register::class.java)
            startActivity(intent)
        }

    }
}
