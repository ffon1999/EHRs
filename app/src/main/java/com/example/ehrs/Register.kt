package com.example.ehrs

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.register.*

class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        val comfirmRegistBtn = findViewById<Button>(R.id.btn_comfirm_register)
        val backpageBtn = findViewById<Button>(R.id.btn_backToLogin)

        btn_comfirm_register.setOnClickListener {
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        }

        btn_backToLogin.setOnClickListener {
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        }
    }

}