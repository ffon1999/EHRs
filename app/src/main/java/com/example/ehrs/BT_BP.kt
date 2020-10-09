package com.example.ehrs

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ideabus.ideabuslibrary.util.BaseUtils
import com.ideabus.model.bluetooth.MyBluetoothLE
import com.ideabus.model.data.*
import com.ideabus.model.protocol.BPMProtocol
import kotlinx.android.synthetic.main.bp_bt.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class BT_BP : AppCompatActivity(), BPMProtocol.OnConnectStateListener,
    BPMProtocol.OnDataResponseListener, BPMProtocol.OnNotifyStateListener,
    MyBluetoothLE.OnWriteStateListener {
    private var button: Button? = null


    private val TAG = "BPMTestActivity"


    private val isSendPersonParam = false
    private var isConnecting = false

    private var userID = "123456789AB"
    private var age = 18


    private var datasys :Int?= null
    private var datadia :Int?= null
    private var datahr :Int?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bp_bt)
        button = findViewById(R.id.info_bt_bp)
        info_bt_bp.setOnClickListener(View.OnClickListener { openDialog() })


        b2.setOnClickListener {
            Global.bpmProtocol.readHistorysOrCurrDataAndSyncTiming()
        }

        /*
        b3.setOnClickListener {
            Global.bpmProtocol.clearAllHistorys()
        }
         */

        b4.setOnClickListener {
            /*
            super.onDestroy()
            if (Global.bpmProtocol.isConnected) Global.bpmProtocol.disconnect()
            Global.bpmProtocol.stopScan()
             */
            if(datasys != null || datadia != null ||datahr!= null){

//                val sharedPreferences = getSharedPreferences("User_Info", Context.MODE_PRIVATE)
//                val editor = sharedPreferences.edit()
//                editor.putFloat("Thermo", datathermo!!)
//                editor.apply()
                datasys?.let { it1 -> datadia?.let { it2 -> datahr?.let { it3 ->
                    connect(it1, it2,
                        it3
                    )
                } } }
            }
            else{
                Toast.makeText(applicationContext, "No data", Toast.LENGTH_SHORT).show()
            }
        }


        initView()
        initParam()

    }
    private fun initView() {
        /*
        toolbar =
            findViewById<View>(R.id.tool_bar) as Toolbar
        bpmList = findViewById<View>(R.id.bpm_list_view) as ListView
        findViewById<View>(R.id.buttonView).visibility = View.GONE
        */
    }
    private fun initParam() {
        //setSupportActionBar(toolbar)

        //Initialize the connection SDK
        Global.bpmProtocol = BPMProtocol.getInstance(this, false, true, Global.sdkid_BPM)
        //toolbar.setSubtitle("Blood Pressure " + Global.bpmProtocol.sdkVersion)
        //logListAdapter = LogListAdapter(this)
        //bpmList.setAdapter(logListAdapter)
    }
    override fun onStart() {
        Log.d(TAG, "1026 onStart")
        super.onStart()
        Global.bpmProtocol.setOnConnectStateListener(this)
        Global.bpmProtocol.setOnDataResponseListener(this)
        Global.bpmProtocol.setOnNotifyStateListener(this)
        Global.bpmProtocol.setOnWriteStateListener(this)
        startScan()
    }
    override fun onDestroy() {
        super.onDestroy()
        if (Global.bpmProtocol.isConnected) Global.bpmProtocol.disconnect()
        Global.bpmProtocol.stopScan()
    }
    private fun startScan() {
        if (!Global.bpmProtocol.isSupportBluetooth(this)) {
            Log.d(TAG, "1026 not support Bluetooth")
            return
        }

        t1.text ="start scan"
        Toast.makeText(applicationContext, "start scan", Toast.LENGTH_SHORT).show()
        //logListAdapter.addLog("start scan")
        Global.bpmProtocol.startScan(10)
    }
    override fun onStop() {
        super.onStop()
        Global.bpmProtocol.disconnect()
        Global.bpmProtocol.stopScan()
    }
    override fun onWriteMessage(isSuccess: Boolean, message: String) {
        //logListAdapter.addLog("WRITE : $message")
        Toast.makeText(applicationContext, "WRITE : $message", Toast.LENGTH_SHORT).show()
    }
    override fun onNotifyMessage(message: String) {
        //logListAdapter.addLog("NOTIFY : $message")
    }
    override fun onResponseReadHistory(dRecord: DRecord) {

        val datarow = dRecord.toString()
        val datatest = datarow+"000000"

        var count = -1.toInt()

        var datacut = "a"

        for (word in  datatest.split("CurrentAndMData{systole")){
            count ++
        }

        var x = 0
        while (count >= 1) {
            datacut = datatest.substringAfterLast("CurrentAndMData")

            count--
        }

        val datasys1 = datacut.substringAfterLast("systole=")
        val datasys2 = datasys1?.substringBeforeLast("month=").toString()
        val datasysfinal = datasys2?.substringBeforeLast(", dia").toString()
        datasys = datasysfinal.toInt()

        val datadia1 = datacut.substringAfterLast(" dia=")
        val datadia2 = datadia1?.substringBeforeLast("month=").toString()
        val datadiafinal = datadia2?.substringBeforeLast(", hr=").toString()
        datadia = datadiafinal.toInt()

        val datahr1 = datacut.substringAfterLast(" hr=")
        val datahr2 = datahr1?.substringBeforeLast("month=").toString()
        val datahrfinal = datahr2?.substringBeforeLast(", day").toString()
        datahr = datahrfinal.toInt()

        //t3.text = "$datacut"
        t4.text = "$datasys"
        t5.text = "$datadia"
        t6.text = "$datahr"
        Log.e("Log","$datatest")
        //logListAdapter.addLog("BPM : ReadHistory -> DRecord = $dRecord")
    }
    override fun onResponseClearHistory(isSuccess: Boolean) {
        //logListAdapter.addLog("BPM : ClearHistory -> isSuccess = $isSuccess")
    }
    override fun onResponseReadUserAndVersionData(
        user: User,
        versionData: VersionData
    ) {
        /*
        logListAdapter.addLog(
            "BPM : ReadUserAndVersionData -> user = " + user +
                    " , versionData = " + versionData
        )

         */
    }
    override fun onResponseWriteUser(isSuccess: Boolean) {
       // logListAdapter.addLog("BPM : WriteUser -> isSuccess = $isSuccess")
    }
    override fun onResponseReadLastData(
        dRecord: CurrentAndMData,
        historyMeasuremeNumber: Int,
        userNumber: Int,
        MAMState: Int,
        isNoData: Boolean
    ) {
        val datarow = dRecord.toString()
        /**/
        val datacut1: String? = datarow.substringAfterLast("systole=")
        val datacut2: String? = datarow.substringAfterLast("dia=")
        val datacut3: String? = datarow.substringAfterLast("hr=")

        val datacut: Float? = datacut1?.substringBeforeLast(", mod").toString().toFloat()

        //t3.text = "systole= $dRecord"
        //t4.text = "dia= $datacut2"
        //t5.text = "hr= $datacut3"
        /*
        logListAdapter.addLog(
            "BPM : ReadLastData -> DRecord = " + dRecord +
                    " historyMeasuremeNumber = " + historyMeasuremeNumber +
                    " userNumber = " + userNumber + " MAMState = " + MAMState +
                    " isNoData = " + isNoData
        )
         */
    }
    override fun onResponseClearLastData(isSuccess: Boolean) {
        //logListAdapter.addLog("BPM : ClearLastData -> isSuccess = $isSuccess")
    }
    override fun onResponseReadDeviceInfo(deviceInfo: DeviceInfo) {
        //logListAdapter.addLog("BPM : ReadDeviceInfo -> DeviceInfo = $deviceInfo")
    }
    override fun onResponseWriteDeviceTime(isSuccess: Boolean) {
        //logListAdapter.addLog("BPM : Write -> DeviceTime = $isSuccess")
    }
    override fun onResponseReadDeviceTime(deviceInfo: DeviceInfo) {
        //logListAdapter.addLog("BPM : Read -> DeviceTime = $deviceInfo")
    }
    override fun onBtStateChanged(isEnable: Boolean) {
        //BLE will be returned when it is turned enable or disable
        if (isEnable) {
            Toast.makeText(this, "BLE is enable!!", Toast.LENGTH_SHORT).show()
            startScan()
        } else {
            Toast.makeText(this, "BLE is disable!!", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onScanResult(mac: String, name: String, rssi: Int) {
        BaseUtils.printLog("d", TAG, "1026 onScanResult:$name")
        //Blood pressure machine
        if (!name.startsWith("n/a")) {
            t2.text = "$name"
            //logListAdapter.addLog("onScanResult：$name mac:$mac rssi:$rssi")
        }
        if (isConnecting) return
        isConnecting = true
        //Stop scanning before connecting
        Global.bpmProtocol.stopScan()
        //Connection
        if (name.startsWith("A")) {
            //logListAdapter.addLog("3G Model！")
            Global.bpmProtocol.connect(mac)
            //findViewById<View>(R.id.button6).visibility = View.VISIBLE
            //findViewById<View>(R.id.button7).visibility = View.VISIBLE
            //findViewById<View>(R.id.button8).visibility = View.GONE
            //findViewById<View>(R.id.button10).visibility = View.GONE
        } else {
            //logListAdapter.addLog("4G Model！")
            Global.bpmProtocol.bond(mac)
            //findViewById<View>(R.id.button6).visibility = View.GONE
            //findViewById<View>(R.id.button7).visibility = View.GONE
            //findViewById<View>(R.id.button8).visibility = View.VISIBLE
            //findViewById<View>(R.id.button10).visibility = View.VISIBLE
        }
    }
    override fun onConnectionState(state: BPMProtocol.ConnectState?) {
        //BLE connection status return, used to judge connection or disconnection
        when (state) {
            BPMProtocol.ConnectState.Connected -> {
                isConnecting = false
                //findViewById<View>(R.id.buttonView).visibility = View.VISIBLE
                //logListAdapter.addLog("Connected")
                t7.text = "Connected"
                Toast.makeText(applicationContext, "Connected", Toast.LENGTH_SHORT).show()
            }
            BPMProtocol.ConnectState.ConnectTimeout -> {
                isConnecting = false
                //findViewById<View>(R.id.buttonView).visibility = View.GONE
                //logListAdapter.addLog("ConnectTimeout")
                Toast.makeText(applicationContext, "ConnectTimeout", Toast.LENGTH_SHORT).show()
            }
            BPMProtocol.ConnectState.Disconnect -> {
                isConnecting = false
                //findViewById<View>(R.id.buttonView).visibility = View.GONE
                //logListAdapter.addLog("Disconnected")
                Toast.makeText(applicationContext, "Disconnected", Toast.LENGTH_SHORT).show()
                startScan()
            }
            BPMProtocol.ConnectState.ScanFinish -> {
                //findViewById<View>(R.id.buttonView).visibility = View.GONE
                //logListAdapter.addLog("ScanFinish")
                startScan()
            }
        }
    }



    fun connect(datasys : Int,datadia : Int , datahr : Int){
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

        val systolic_blood_pressure: Int = datasys
        val diastolic_blood_pressure: Int = datadia
        val heart_rate: Int = datahr

        val nameU =sharedPreferences.getString("Username","")

        jsonobj1.put("user_name_patient",nameU)
        jsonobj1.put("systolic_blood_pressure_patient",systolic_blood_pressure)
        jsonobj1.put("diastolic_blood_pressure_patient",diastolic_blood_pressure)
        jsonobj1.put("heart_rate_patient",heart_rate)
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
                    editor.putInt("NumberBPHeight",systolic_blood_pressure)
                    editor.putInt("NumberBPLow",diastolic_blood_pressure)
                    editor.putInt("NumberPR",heart_rate)
                    editor.apply()

                    /*
                    if (Global.thermoProtocol.isConnected) Global.thermoProtocol.disconnect()
                    Global.thermoProtocol.stopScan()
                     */

                    val intent = Intent(this@BT_BP, Home::class.java)
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
        val infoBPdialog = HowToSyncBP()
        infoBPdialog.show(supportFragmentManager, "how to sync blood pressure dialog")
    }
}