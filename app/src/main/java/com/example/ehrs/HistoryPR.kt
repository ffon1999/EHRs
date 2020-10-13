package com.example.ehrs

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils
import kotlinx.android.synthetic.main.log_history_bp.*
import kotlinx.android.synthetic.main.log_history_pr.*

class HistoryPR : AppCompatActivity() {

    private val list = ArrayList<DataOther>()
    private var mChart: LineChart? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_history_pr)
        mChart = findViewById(R.id.chart)
        /*val mv = MyMarkerView(applicationContext, R.layout.custom_marker_view)
        mv.chartView = mChart*/
        renderData()
        callListBP()

    }

    private fun renderData() {
        /*set graph height and width
        val llXAxis = LimitLine(10f, "ค่าความดันเลือด")
        llXAxis.lineWidth = 10f
        llXAxis.enableDashedLine(10f, 10f, 0f)
        llXAxis.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
        llXAxis.textSize = 50f*/
        //set x axis
        val xAxis = mChart!!.xAxis
        xAxis.enableGridDashedLine(10f, 10f, 0f) //set Dashed Line that dragged from x axis
        //set number of x point
        xAxis.axisMaximum = 6f
        xAxis.axisMinimum = 0f
        xAxis.position = XAxis.XAxisPosition.BOTTOM //set x axis position
        //xAxis.setDrawLimitLinesBehindData(true)
        //set min max line
        val ll1 = LimitLine(215f, "Maximum Limit")//set max line point
        ll1.lineWidth = 4f
        ll1.enableDashedLine(10f, 10f, 0f)
        ll1.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
        ll1.textSize = 10f
        val ll2 = LimitLine(70f, "Minimum Limit")//set min line point
        ll2.lineWidth = 4f
        ll2.enableDashedLine(10f, 10f, 0f)
        ll2.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM //set position of Minimum Limit
        ll2.textSize = 10f
        //set y axis
        val leftAxis = mChart!!.axisLeft
        //leftAxis.removeAllLimitLines()
        leftAxis.addLimitLine(ll1)//add max point line
        leftAxis.addLimitLine(ll2)//add min point line
        leftAxis.axisMaximum = 300f //set max of y axis
        leftAxis.axisMinimum = 0f //set min of y axis
        leftAxis.enableGridDashedLine(10f, 10f, 0f)
        //leftAxis.setDrawZeroLine(false)
        //leftAxis.setDrawLimitLinesBehindData(false)
        mChart!!.axisRight.isEnabled = false //remove chart frame
        setData()
    }
    private fun setData() {

        val daaa = floatArrayOf(0f,1f,2f,3f,4f,5f,6f)
        var daaa2 = floatArrayOf(0f,0f,0f,0f,0f,0f,0f)
        var xAxisLabels = mutableListOf("1", "2", "3", "4", "5", "6" ,"7") // set text to x axis
        var dataday= mutableListOf("1", "2", "3", "4", "5", "6" ,"7")
        //val oo =      calldata()
        val url = "https://ehr-system-project.herokuapp.com/api/examination/graph/heartr/"
        val sharedPreferences = getSharedPreferences("User_Info", Context.MODE_PRIVATE)
        val username =sharedPreferences.getString("Username","Username")
        //val daaa = floatArrayOf(0f,1f,2f,3f,4f,5f,6f)
        val request = JsonArrayRequest(
            Request.Method.GET,url + "$username",null,
            Response.Listener {
                for(i in 0 until it.length()){
                    val jsonObj = it.getJSONObject(i)

                    val date = jsonObj.getString("_id").trim()
                    val systolic = jsonObj.getString("avgData").toFloat()

                    daaa2[i] = systolic
                    dataday[i] = date

                }

                val tset = daaa2[6]
                Log.e("e","S11 $tset")
                //set value to data
                val values = ArrayList<Entry>()

                var daset1 = floatArrayOf(0f,0f,0f,0f,0f,0f,0f)

                var count = 0
                for (i in 6 downTo 0){
                    val da  = daaa2[i]
                    val date =dataday[i]
                    daset1[count] = da
                    xAxisLabels[count] = date
                    count++
                }
                Log.e("e","S11 $tset")
                for (i in 0 until 7){
                    val ddd = daaa[i]
                    val ddd2 = daset1[i]
                    values.add(Entry(ddd, ddd2))
                }


                mChart!!.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabels)

                val set1: LineDataSet
                // set value to data
                if (mChart!!.data != null &&
                    mChart!!.data.dataSetCount > 0) {
                    set1 = mChart!!.data.getDataSetByIndex(0) as LineDataSet
                    set1.values = values
                    //mChart!!.data.notifyDataChanged()
                    //mChart!!.notifyDataSetChanged()
                } else {
                    set1 = LineDataSet(values, "Sample Data")
                    set1.setDrawIcons(false)
                    //set1.enableDashedLine(10f, 5f, 0f) //set Dashed line
                    //set1.enableDashedHighlightLine(20f, 5f, 0f)
                    set1.color = Color.DKGRAY //set line and Simple Data color
                    set1.setCircleColor(Color.DKGRAY)//set point color
                    set1.lineWidth = 1f
                    set1.circleRadius = 3f //set point size
                    set1.setDrawCircleHole(false) //set hole in point
                    set1.valueTextSize = 16f//set text above point size
                    set1.setDrawFilled(true)//set color fill to chart
                    //set1.formLineWidth = 20f
                    //set1.formLineDashEffect = DashPathEffect(floatArrayOf(50f, 5f), 0f)
                    set1.formSize = 15f//set simple data size
                    //set Curve graph
                    set1.mode = LineDataSet.Mode.CUBIC_BEZIER
                    set1.cubicIntensity = 0.2f
                    //set color shade to chart
                    if (Utils.getSDKInt() >= 18) {
                        val drawable = ContextCompat.getDrawable(this, R.drawable.graph_color)
                        set1.fillDrawable = drawable
                    } else {
                        set1.fillColor = Color.DKGRAY
                    }
                    //set value to data
                    val dataSets = ArrayList<ILineDataSet>()
                    dataSets.add(set1)
                    val data = LineData(dataSets)
                    mChart!!.data = data
                }
            },
            Response.ErrorListener { Toast.makeText(this, "Something went wrong!! $it", Toast.LENGTH_SHORT).show()
                Log.e("eeee","$it")
            })

        val queue = Volley.newRequestQueue(this)
        queue.add(request)


    }


    fun callListBP(){
        val url = "https://ehr-system-project.herokuapp.com/api/examination/list/heartr/"
        val sharedPreferences = getSharedPreferences("User_Info", Context.MODE_PRIVATE)
        val username =sharedPreferences.getString("Username","Username")

        val request = JsonArrayRequest(
            Request.Method.GET,url + "$username",null,
            Response.Listener {
                for(i in 0 until it.length()){
                    val jsonObj = it.getJSONObject(i)

                    val date = jsonObj.getString("date_add")
                    val time = jsonObj.getString("time_add")
                    val datahis = jsonObj.getString("heart_rate_patient")

                    val data = DataOther(date,time,datahis)
                    list.add(data)
                    //create adapter
                    history_pr.setHasFixedSize(true)
                    history_pr.layoutManager = LinearLayoutManager(this)
                    val adapter = RvAdapterOther(this,list)
                    history_pr.adapter = adapter

                }
            },
            Response.ErrorListener { Toast.makeText(this, "Something went wrong!! $it", Toast.LENGTH_SHORT).show()
                Log.e("eeee","$it")
            })

        val queue = Volley.newRequestQueue(this)
        queue.add(request)


    }

}