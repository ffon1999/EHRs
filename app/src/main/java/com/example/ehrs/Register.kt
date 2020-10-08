package com.example.ehrs

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.input_mn_bp.*
import kotlinx.android.synthetic.main.register.*
import org.json.JSONObject

class Register : AppCompatActivity() {
    val url = "https://ehr-system-project.herokuapp.com/api/patient"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        val comfirmRegistBtn = findViewById<Button>(R.id.btn_comfirm_register)
        val backpageBtn = findViewById<Button>(R.id.btn_backToLogin)



        btn_comfirm_register.setOnClickListener {
            val username: String = input_regis_username.text.toString()
            val password: String = input_regis_password.text.toString()
            val password_confirm: String = input_regis_password_confirm.text.toString()
            val firstname: String = input_regis_firstname.text.toString()
            val surname: String = input_regis_surname.text.toString()
            val phone: String = input_regis_phone.text.toString()
            val email: String = input_regis_email.text.toString()

            /*   set Date to JSONObject */
            val jsonobjAddUser = JSONObject()
            jsonobjAddUser.put("user_name_patient",username)
            jsonobjAddUser.put("password_patient",password)
            jsonobjAddUser.put("name_patient",firstname)
            jsonobjAddUser.put("surname_patient",surname)
            jsonobjAddUser.put("mobile_phone_number_patient",phone)
            jsonobjAddUser.put("email_patient",email)

/*    */
            val que = Volley.newRequestQueue(this)
            val request = JsonObjectRequest(
                Request.Method.POST,url,jsonobjAddUser,
                Response.Listener { response ->
                    // Process the json

                    try {
                        val obj = response

                        Toast.makeText(applicationContext, "Volley $response "  , Toast.LENGTH_SHORT).show()

                        val intent = Intent(this,Login::class.java)
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




        btn_backToLogin.setOnClickListener {
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        }
    }

}