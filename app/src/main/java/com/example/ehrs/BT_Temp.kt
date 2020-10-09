package com.example.ehrs

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ideabus.model.bluetooth.MyBluetoothLE
import com.ideabus.model.data.ThermoMeasureData
import com.ideabus.model.protocol.ThermoProtocol
import kotlinx.android.synthetic.main.home.*
import kotlinx.android.synthetic.main.temp_bt.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class BT_Temp  : AppCompatActivity(), ThermoProtocol.OnDataResponseListener,
    ThermoProtocol.OnConnectStateListener, ThermoProtocol.OnNotifyStateListener,
    MyBluetoothLE.OnWriteStateListener {

    private var button: Button? = null
    var expandableViewTemp : LinearLayout? = null
    var startSyncTemp : Button? = null

    private var datathermo :Float?= null
    private var isConnecting = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.temp_bt)

        initParam()

        // how to sync btn
        button = findViewById(R.id.info_bt_temp)
        expandableViewTemp = findViewById(R.id.expandableViewBTTemp)
        startSyncTemp = findViewById(R.id.start_sync_temp)

        start_sync_temp.setOnClickListener(View.OnClickListener {
            if (expandableViewBTTemp.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(expandableViewTemp, AutoTransition())
                expandableViewBTTemp.setVisibility(View.VISIBLE)
            }
            /**/
            Global.thermoProtocol.setOnDataResponseListener(this)
            Global.thermoProtocol.setOnConnectStateListener(this)
            Global.thermoProtocol.setOnNotifyStateListener(this)
            Global.thermoProtocol.setOnWriteStateListener(this)
            //Start scan
            startScan()

        })
        update_bt_temp.setOnClickListener{
            if (Global.thermoProtocol.isConnected) Global.thermoProtocol.disconnect()
            Global.thermoProtocol.stopScan()

            if(datathermo != null){

//                val sharedPreferences = getSharedPreferences("User_Info", Context.MODE_PRIVATE)
//                val editor = sharedPreferences.edit()
//                editor.putFloat("Thermo", datathermo!!)
//                editor.apply()
                connect(datathermo!!)

            }
            else{
                Toast.makeText(applicationContext, "No data", Toast.LENGTH_SHORT).show()
            }
        }



        // press how to sync BT btn
        info_bt_temp.setOnClickListener(View.OnClickListener { openDialog() })
    }

    override fun onDestroy() {
        super.onDestroy()
        if (Global.thermoProtocol.isConnected) Global.thermoProtocol.disconnect()
        Global.thermoProtocol.stopScan()
    }

    /* set get protocal */
    private fun initParam() {
        //Initialize the connection SDK
        Global.thermoProtocol = ThermoProtocol.getInstance(this, false, true, Global.sdkid_BT)
        //toolbar.setSubtitle("Body Temperature " + Global.thermoProtocol.getSDKVersion());
    }
    private fun startScan() {
        if (!Global.thermoProtocol.isSupportBluetooth(this)) {
            return
        }
        startScan.text = "กำลังค้นหาอุปกรณ์"
        Toast.makeText(applicationContext, "start scan", Toast.LENGTH_SHORT).show()
        //logListAdapter.addLog("start scan");
        /* set delay time 5 second*/
        Global.thermoProtocol.startScan(5)
    }
    override fun onStop() {
        super.onStop()
        Global.thermoProtocol.disconnect()
        Global.thermoProtocol.stopScan()
    }
    override fun onResponseDeviceInfo(
        macAddress: String?,
        workMode: Int,
        batteryVoltage: Float
    ) {
        /* logListAdapter.addLog("THERMO : DeviceInfo -> macAddress = " + macAddress +
                " , workMode = " + workMode + " , batteryVoltage = " + batteryVoltage);
        */
    }
    override fun onResponseUploadMeasureData(data: ThermoMeasureData) {
        val Thermo = data.toString()
        /**/
        val datacut1: String? = Thermo.substringAfterLast("measureTemperature=")
        val datacut2: Float? = datacut1?.substringBeforeLast(", mod").toString().toFloat()

        datathermo = datacut2
        THERMO_Measurement.text = "$datathermo °C"
        //logListAdapter.addLog("THERMO : UploadMeasureData -> ThermoMeasureData = " + data);
    }
    override fun onBtStateChanged(isEnable: Boolean) {
        //BLE will be returned when it is turned enable or disable
        if (isEnable) {
            Toast.makeText(this, "BLE is enable!!", Toast.LENGTH_SHORT).show()
            startScan()
        } else Toast.makeText(this, "BLE is disable!!", Toast.LENGTH_SHORT).show()
    }
    override fun onScanResult(mac: String, name: String, rssi: Int) {
        //Temperature
        if (!name.startsWith("n/a")) {
            onScanResult.text = "$name"
            //logListAdapter.addLog("onScanResult："+name+" mac:"+mac+" rssi:"+rssi);
        }
        if (isConnecting) return
        isConnecting = true

        //Stop scanning before connecting
        Global.thermoProtocol.stopScan()
        //Connection
        Global.thermoProtocol.connect(mac)
    }
    override fun onConnectionState(state: ThermoProtocol.ConnectState?) {
        //BLE connection status return, used to judge connection or disconnection
        when (state) {
            ThermoProtocol.ConnectState.Connected -> {
                isConnecting = false
                finalProcess.text = "Connected"
            }
            ThermoProtocol.ConnectState.ConnectTimeout -> isConnecting = false
            ThermoProtocol.ConnectState.Disconnect -> {
                isConnecting = false
                //textView5!!.text = "Disconnected"
                //logListAdapter.addLog("Disconnected");
                startScan()
            }
            ThermoProtocol.ConnectState.ScanFinish ->                // logListAdapter.addLog("ScanFinish");
                startScan()
        }
    }

    override fun onWriteMessage(isSuccess: Boolean, message: String?) {
        // logListAdapter.addLog("WRITE : " + message);
    }

    override fun onNotifyMessage(message: String?) {
        //logListAdapter.addLog("NOTIFY : " + message);
    }


    fun connect(dataThermo : Float){
        val url = "https://ehr-system-project.herokuapp.com/api/examination"
        val sharedPreferences = getSharedPreferences("User_Info", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        /*   set Date  */
        var answerdate1 : String = "1111"
        var answertime2 : String = "2222"
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

        val jsonobj1 = JSONObject()

        val body_temperature: Float = dataThermo

        val nameU =sharedPreferences.getString("Username","")

        jsonobj1.put("user_name_patient",nameU)
        jsonobj1.put("body_temperature_patient",body_temperature)
        jsonobj1.put("date_add",answerdate1)
        jsonobj1.put("time_add",answertime2)

        val que = Volley.newRequestQueue(this)

        val request = JsonObjectRequest(
            Request.Method.POST,url,jsonobj1,
            Response.Listener { response ->
                /* Process the json*/

                try {
                    val obj = response
                    Toast.makeText(applicationContext, "Volley $response "  , Toast.LENGTH_SHORT).show()

                    editor.putFloat("NumberTemp",body_temperature)
                    editor.apply()

                    if (Global.thermoProtocol.isConnected) Global.thermoProtocol.disconnect()
                    Global.thermoProtocol.stopScan()

                    val intent = Intent(this@BT_Temp, Home::class.java)
                    startActivity(intent)
                }catch (e:Exception){
                    /*textView10.text = "Exception: $e "*/
                }

            }, Response.ErrorListener{

                Toast.makeText(applicationContext, "Volley error $it"  , Toast.LENGTH_SHORT).show()

            })
        que.add(request)

    }

    fun openDialog() {
        val infoTempdialog = HowToSyncTemp()
        infoTempdialog.show(supportFragmentManager, "how to sync temperature dialog")
    }
}