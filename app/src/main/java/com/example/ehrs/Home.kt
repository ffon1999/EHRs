package com.example.ehrs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.home.*
import kotlinx.android.synthetic.main.login.*
import org.json.JSONObject

class Home : AppCompatActivity() {

    var expendableViewBP: LinearLayout? = null
    var expendableViewPR: LinearLayout? = null
    var expendableViewTemp: LinearLayout? = null
    var expendableViewWeight: LinearLayout? = null
    var expendableViewHeight: LinearLayout? = null
    var expendableViewGlucose: LinearLayout? = null

    var cvBP: CardView? = null
    var cvPR: CardView? = null
    var cvTemp: CardView? = null
    var cvWeight: CardView? = null
    var cvHeight: CardView? = null
    var cvGlucose: CardView? = null

    var HistoryBP: Button? = null
    var HistoryPR: Button? = null
    var HistoryTemp: Button? = null
    var HistoryWeight: Button? = null
    var HistoryHeight: Button? = null
    var HistoryGlucose: Button? = null

    var addBP : Button? = null
    var addPR : Button? = null
    var addTemp : Button? = null
    var addWeight : Button? = null
    var addHeight : Button? = null
    var addGlucose : Button? = null

    var addBT_BP : Button? = null
    var addBT_Temp : Button? = null

    var BtnAccount : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        val sharedPreferences = getSharedPreferences("User_Info", Context.MODE_PRIVATE)

        val username =sharedPreferences.getString("Username","Username")
        val numberBPHeight =sharedPreferences.getInt("NumberBPHeight",0)
        val numberBPLow =sharedPreferences.getInt("NumberBPLow",0)
        val numberPR =sharedPreferences.getInt("NumberPR",0)
        val numberTemp =sharedPreferences.getFloat("NumberTemp",0f)
        val numberWeight =sharedPreferences.getFloat("NumberWeight",0f)
        val numberHeight =sharedPreferences.getFloat("NumberHeight",0f)
        val numberGlucose =sharedPreferences.getInt("NumberGlucose",0)

        /* Set data to text  */
        username_home.text = "$username"
        num1_bp.text = "$numberBPHeight"
        num2_bp.text = "$numberBPLow"
        num_pr.text = "$numberPR"
        num_temp.text = "$numberTemp"
        num_weight.text = "$numberWeight"
        num_height.text = "$numberHeight"
        num_glucose.text = "$numberGlucose"

        expendableViewBP = findViewById(R.id.expandableViewBP)
        expendableViewPR = findViewById(R.id.expandableViewPR)
        expendableViewTemp = findViewById(R.id.expandableViewTemp)
        expendableViewWeight = findViewById(R.id.expandableViewWeight)
        expendableViewHeight = findViewById(R.id.expandableViewHeight)
        expendableViewGlucose = findViewById(R.id.expandableViewGlucose)

        cvBP = findViewById(R.id.cardview_bp)
        cvPR = findViewById(R.id.cardview_pr)
        cvTemp = findViewById(R.id.cardview_temp)
        cvWeight = findViewById(R.id.cardview_weight)
        cvHeight = findViewById(R.id.cardview_height)
        cvGlucose = findViewById(R.id.cardview_glucose)

        HistoryBP = findViewById(R.id.btn_historyBP)
        HistoryPR = findViewById(R.id.btn_historyPR)
        HistoryTemp = findViewById(R.id.btn_historyTemp)
        HistoryWeight = findViewById(R.id.btn_historyWeight)
        HistoryHeight = findViewById(R.id.btn_historyHeight)
        HistoryGlucose = findViewById(R.id.btn_historyGlucose)


        addBT_BP = findViewById(R.id.btn_addBT_BP)
        addBT_Temp = findViewById(R.id.btn_addBT_Temp)

        BtnAccount = findViewById(R.id.btn_account)

        val autoTransition = AutoTransition()
        autoTransition.duration = 50000


        //press btn to expandable cardview
        cardview_bp.setOnClickListener(View.OnClickListener {
            if (expandableViewBP.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(cvBP, AutoTransition())
                expandableViewBP.setVisibility(View.VISIBLE)
            } else {
                TransitionManager.beginDelayedTransition(expendableViewBP, AutoTransition())
                expandableViewBP.setVisibility(View.GONE)
            }
        })

        cardview_pr.setOnClickListener(View.OnClickListener {
            if (expandableViewPR.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(cvPR, AutoTransition())
                expandableViewPR.setVisibility(View.VISIBLE)
            } else {
                TransitionManager.beginDelayedTransition(expendableViewPR, AutoTransition())
                expandableViewPR.setVisibility(View.GONE)
            }
        })

        cardview_temp.setOnClickListener(View.OnClickListener {
            if (expandableViewTemp.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(cvTemp, AutoTransition())
                expandableViewTemp.setVisibility(View.VISIBLE)
            } else {
                TransitionManager.beginDelayedTransition(expendableViewTemp, AutoTransition())
                expandableViewTemp.setVisibility(View.GONE)
            }
        })

        cardview_weight.setOnClickListener(View.OnClickListener {
            if (expandableViewWeight.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(cvWeight, AutoTransition())
                expandableViewWeight.setVisibility(View.VISIBLE)
            } else {
                TransitionManager.beginDelayedTransition(expandableViewWeight, AutoTransition())
                expandableViewWeight.setVisibility(View.GONE)
            }
        })

        cardview_height.setOnClickListener(View.OnClickListener {
            if (expandableViewHeight.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(cvHeight, AutoTransition())
                expandableViewHeight.setVisibility(View.VISIBLE)
            } else {
                TransitionManager.beginDelayedTransition(expendableViewHeight, AutoTransition())
                expandableViewHeight.setVisibility(View.GONE)
            }
        })

        cardview_glucose.setOnClickListener(View.OnClickListener {
            if (expandableViewGlucose.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(cvGlucose, AutoTransition())
                expandableViewGlucose.setVisibility(View.VISIBLE)
            } else {
                TransitionManager.beginDelayedTransition(expendableViewGlucose, AutoTransition())
                expandableViewGlucose.setVisibility(View.GONE)
            }
        })


        //press btn to see history
        btn_historyBP.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(this@Home, com.example.ehrs.HistoryBP::class.java)
            )
        })

        btn_historyPR.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(this@Home, com.example.ehrs.HistoryPR::class.java)
            )
        })

        btn_historyTemp.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(this@Home, com.example.ehrs.HistoryTemperature::class.java)
            )
        })

        btn_historyWeight.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(this@Home, com.example.ehrs.HistoryWeight::class.java)
            )
        })

        btn_historyHeight.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(this@Home, com.example.ehrs.HistoryHeight::class.java)
            )
        })
        btn_historyGlucose.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(this@Home, com.example.ehrs.HistoryGlucose::class.java)
            )
        })

        //press btn to add manual data
        btn_adddataBP.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(this@Home, com.example.ehrs.AddBP::class.java)
            )
        })

        btn_adddataPR.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(this@Home, com.example.ehrs.AddPR::class.java)
            )
        })

        btn_adddataTemp.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(this@Home, com.example.ehrs.AddTemp::class.java)
            )
        })

        btn_adddataWeight.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(this@Home, com.example.ehrs.AddWeight::class.java)
            )
        })

        btn_adddataHeight.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(this@Home, com.example.ehrs.AddHeight::class.java)
            )
        })
        btn_adddataGlucose.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(this@Home, com.example.ehrs.AddGlucose::class.java)
            )
        })

        //press btn to sync data with bluetooth
        btn_addBT_BP.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(this@Home, BT_BP::class.java)
            )
        })
        btn_addBT_PR.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(this@Home, BT_BP::class.java)
            )
        })
        btn_addBT_Temp.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(this@Home, BT_Temp::class.java)
            )
        })


        //press btn goto profile page
        btn_account.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(this@Home, Profile_Account::class.java)
            )
        })

        buttonrefresh.setOnClickListener {
            refreshall()
        }

    }

    override fun onBackPressed() {
        // super.onBackPressed();
        // Not calling **super**, disables back button in current screen.
    }

    fun refreshall(){

        refreshBP()
        refreshHR()
        refreahTemp()
        refreahWeight()
        refreahHeight()
        refreahGlucose()

    }
    fun refreshBP(){
        val url = "https://ehr-system-project.herokuapp.com/api/examination/refresh/BP"
        val jsonobjusername = JSONObject()
        val sharedPreferences = getSharedPreferences("User_Info", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val username =sharedPreferences.getString("Username","Username")
        jsonobjusername.put("user_name_patient",username)

        val que = Volley.newRequestQueue(this)

        val request = JsonObjectRequest(
            Request.Method.POST,url,jsonobjusername,
            Response.Listener { response ->
                // Process the json
                try {
                    val obj = response
                    //Toast.makeText(applicationContext, "Volley $response "  , Toast.LENGTH_SHORT).show()
                    val NumberBPHeight = obj.getInt("systolic_blood_pressure_patient")
                    val NumberBPLow = obj.getInt("diastolic_blood_pressure_patient")
                    //val password = obj.getString("password_patient")

                    //***//
                    editor.putString("Username", username)
                    //editor.putString("Password", password)
                    editor.putInt("NumberBPHeight", NumberBPHeight)
                    editor.putInt("NumberBPLow", NumberBPLow)

                    //**---**
                    editor.apply()
                    val numberBPHeight =sharedPreferences.getInt("NumberBPHeight",0)
                    val numberBPLow =sharedPreferences.getInt("NumberBPLow",0)
                    num1_bp.text = "$numberBPHeight"
                    num2_bp.text = "$numberBPLow"
                    Log.e("e","0000000000000000 ")
                    //val nameU =sharedPreferences.getString("NAME","")
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
    fun refreshHR(){
        val url = "https://ehr-system-project.herokuapp.com/api/examination/refresh/heartrate"
        val jsonobjusername = JSONObject()
        val sharedPreferences = getSharedPreferences("User_Info", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val username =sharedPreferences.getString("Username","Username")
        jsonobjusername.put("user_name_patient",username)

        val que = Volley.newRequestQueue(this)

        val request = JsonObjectRequest(
            Request.Method.POST,url,jsonobjusername,
            Response.Listener { response ->
                // Process the json
                try {
                    val obj = response
                    //Toast.makeText(applicationContext, "Volley $response "  , Toast.LENGTH_SHORT).show()
                    val NumberPR = obj.getInt("heart_rate_patient")
                    //val password = obj.getString("password_patient")

                    //***//
                    editor.putString("Username", username)
                    //editor.putString("Password", password)
                    editor.putInt("NumberPR", NumberPR)

                    //**---**
                    editor.apply()
                    val numberPR =sharedPreferences.getInt("NumberPR",0)
                    num_pr.text = "$numberPR"
                    Log.e("e","111111111 ")
                    //val nameU =sharedPreferences.getString("NAME","")
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
    fun refreahTemp(){
        val url = "https://ehr-system-project.herokuapp.com/api/examination/refresh/temp"
        val jsonobjusername = JSONObject()
        val sharedPreferences = getSharedPreferences("User_Info", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val username =sharedPreferences.getString("Username","Username")
        jsonobjusername.put("user_name_patient",username)

        val que = Volley.newRequestQueue(this)

        val request = JsonObjectRequest(
            Request.Method.POST,url,jsonobjusername,
            Response.Listener { response ->
                // Process the json
                try {
                    val obj = response
                    //Toast.makeText(applicationContext, "Volley $response "  , Toast.LENGTH_SHORT).show()
                    val NumberTemp = obj.getString("body_temperature_patient").toFloat()
                    //val password = obj.getString("password_patient")

                    //***//
                    editor.putFloat("NumberTemp", NumberTemp)

                    //**---**
                    editor.apply()
                    val numberTemp =sharedPreferences.getFloat("NumberTemp",0f)
                    num_temp.text = "$numberTemp"
                    Log.e("e","2222222222 ")
                    //val nameU =sharedPreferences.getString("NAME","")
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
    fun refreahWeight(){
        val url = "https://ehr-system-project.herokuapp.com/api/examination/refresh/weight"
        val jsonobjusername = JSONObject()
        val sharedPreferences = getSharedPreferences("User_Info", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val username =sharedPreferences.getString("Username","Username")
        jsonobjusername.put("user_name_patient",username)

        val que = Volley.newRequestQueue(this)

        val request = JsonObjectRequest(
            Request.Method.POST,url,jsonobjusername,
            Response.Listener { response ->
                // Process the json
                try {
                    val obj = response
                    //Toast.makeText(applicationContext, "Volley $response "  , Toast.LENGTH_SHORT).show()
                    val NumberWeight = obj.getString("weight_patient").toFloat()
                    //val password = obj.getString("password_patient")

                    //***//
                    editor.putFloat("NumberWeight", NumberWeight)

                    //**---**
                    editor.apply()
                    val numberWeight =sharedPreferences.getFloat("NumberWeight",0f)
                    num_weight.text = "$numberWeight"
                    Log.e("e","333333333333 ")

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
    fun refreahHeight(){
        val url = "https://ehr-system-project.herokuapp.com/api/examination/refresh/height"
        val jsonobjusername = JSONObject()
        val sharedPreferences = getSharedPreferences("User_Info", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val username =sharedPreferences.getString("Username","Username")
        jsonobjusername.put("user_name_patient",username)

        val que = Volley.newRequestQueue(this)

        val request = JsonObjectRequest(
            Request.Method.POST,url,jsonobjusername,
            Response.Listener { response ->
                // Process the json
                try {
                    val obj = response
                    //Toast.makeText(applicationContext, "Volley $response "  , Toast.LENGTH_SHORT).show()
                    val NumberHeight = obj.getString("height_patient").toFloat()
                    //val password = obj.getString("password_patient")

                    //***//
                    editor.putFloat("NumberHeight", NumberHeight)

                    //**---**
                    editor.apply()
                    val numberHeight =sharedPreferences.getFloat("NumberHeight",0f)
                    num_height.text = "$numberHeight"
                    Log.e("e","444444444444444 ")
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
    fun refreahGlucose(){
        val url = "https://ehr-system-project.herokuapp.com/api/examination/refresh/glucose"
        val jsonobjusername = JSONObject()
        val sharedPreferences = getSharedPreferences("User_Info", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val username =sharedPreferences.getString("Username","Username")
        jsonobjusername.put("user_name_patient",username)

        val que = Volley.newRequestQueue(this)

        val request = JsonObjectRequest(
            Request.Method.POST,url,jsonobjusername,
            Response.Listener { response ->
                // Process the json
                try {
                    val obj = response
                    //Toast.makeText(applicationContext, "Volley $response "  , Toast.LENGTH_SHORT).show()
                    val NumberGlucose = obj.getInt("glucose_patient")
                    //val password = obj.getString("password_patient")

                    //***//
                    editor.putInt("NumberGlucose", NumberGlucose)

                    //**---**
                    editor.apply()
                    val numberGlucose =sharedPreferences.getInt("NumberGlucose",0)
                    num_glucose.text = "$numberGlucose"
                    Log.e("e","555555555555")
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

}