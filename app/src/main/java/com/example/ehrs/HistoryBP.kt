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
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils
import kotlinx.android.synthetic.main.log_history_bp.*

class HistoryBP :AppCompatActivity() {
    private val list = ArrayList<DataBP>()
    private var mChart: LineChart? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_history_bp)

        mChart = findViewById(R.id.chart)
        /*val mv = MyMarkerView(applicationContext, R.layout.custom_marker_view)
        mv.chartView = mChart*/
        renderData()

        callListBP()
    }


    private fun renderData() {
        //set graph height and width
        /*llXAxis = LimitLine(10f, "ค่าความดันเลือด")
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
        xAxis.setLabelRotationAngle(330f);
        mChart!!.getXAxis().setTextSize(12F);
        mChart!!.getXAxis().setDrawGridLines(false);


        //xAxis.setDrawLimitLinesBehindData(true)
        //set min max line
        val ll1 = LimitLine(160f, "SYS")//set max line point
        ll1.lineWidth = 2f
        ll1.enableDashedLine(10f, 10f, 0f)
        ll1.labelPosition = LimitLine.LimitLabelPosition.LEFT_TOP
        ll1.textSize = 10f
        val ll2 = LimitLine(80f, "DIA")//set min line point
        ll2.lineWidth = 2f
        ll2.enableDashedLine(10f, 10f, 0f)
        ll2.labelPosition = LimitLine.LimitLabelPosition.LEFT_BOTTOM //set position of Minimum Limit
        ll2.textSize = 10f
        //set y axis
        val leftAxis = mChart!!.axisLeft
        //leftAxis.removeAllLimitLines()
        leftAxis.addLimitLine(ll1)//add max point line
        leftAxis.addLimitLine(ll2)//add min point line
        leftAxis.axisMaximum = 200f //set max of y axis
        leftAxis.axisMinimum = 0f //set min of y axis
        leftAxis.enableGridDashedLine(10f, 10f, 0f)
        //leftAxis.setDrawZeroLine(false)
        //leftAxis.setDrawLimitLinesBehindData(false)

        mChart!!.getAxisLeft().setTextSize(12F);
        mChart!!.getAxisLeft().setDrawGridLines(false)
        mChart!!.axisRight.isEnabled = false //remove chart frame
        setData()
    }
    private fun setData() {

        val daaa = floatArrayOf(0f,1f,2f,3f,4f,5f,6f)

        var daaa2 = floatArrayOf(0f,0f,0f,0f,0f,0f,0f)
        var daaa3 = floatArrayOf(0f,0f,0f,0f,0f,0f,0f)
        var xAxisLabels = mutableListOf("1", "2", "3", "4", "5", "6" ,"7") // set text to x axis
        var dataday= mutableListOf("1", "2", "3", "4", "5", "6" ,"7")
        //val oo = calldata()
        val url = "https://ehr-system-project.herokuapp.com/api/examination/graph/bloodp/"
        val sharedPreferences = getSharedPreferences("User_Info", Context.MODE_PRIVATE)
        val username =sharedPreferences.getString("Username","Username")
        //val daaa = floatArrayOf(0f,1f,2f,3f,4f,5f,6f)
        val request = JsonArrayRequest(
            Request.Method.GET,url + "$username",null,
            Response.Listener {
                for(i in 0 until it.length()){
                    val jsonObj = it.getJSONObject(i)

                    val date = jsonObj.getString("_id").trim()
                    val systolic = jsonObj.getString("avgDatasys").toFloat()
                    val diastolic = jsonObj.getString("avgDatadia").toFloat()

                    daaa2[i] = systolic
                    daaa3[i] = diastolic
                    dataday[i] = date

                }

                val tset = daaa2[6]
                Log.e("e","S11 $tset")
                //set value to data
                val values = ArrayList<Entry>()
                val values2 = ArrayList<Entry>()

                var daset1 = floatArrayOf(0f,0f,0f,0f,0f,0f,0f)
                var daset3 = floatArrayOf(0f,0f,0f,0f,0f,0f,0f)

                var count = 0
                for (i in 6 downTo 0){
                    val da  = daaa2[i]
                    val da3  = daaa3[i]

                    val date =dataday[i]
                    daset1[count] = da
                    daset3[count] = da3
                    xAxisLabels[count] = date
                    count++
                }
                Log.e("e","S11 $tset")
                for (i in 0 until 7){
                    val ddd = daaa[i]
                    val ddd2 = daset1[i]
                    val ddd3 = daset3[i]
                    values.add(Entry(ddd, ddd2))
                    values2.add(Entry(ddd, ddd3))
                }


                mChart!!.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabels)


                val set1: LineDataSet
                val set2: LineDataSet
                // set value to data
                if (mChart!!.data != null &&
                    mChart!!.data.dataSetCount > 0) {
                    set1 = mChart!!.data.getDataSetByIndex(0) as LineDataSet
                    set2 = mChart!!.data.getDataSetByIndex(1) as LineDataSet
                    set1.values = values
                    set2.values = values2
                    //mChart!!.data.notifyDataChanged()
                    //mChart!!.notifyDataSetChanged()
                } else {

                    set1 = LineDataSet(values, "SYS")
                    set1.setDrawIcons(false)
                    //set1.enableDashedLine(10f, 5f, 0f) //set Dashed line
                    //set1.enableDashedHighlightLine(20f, 5f, 0f)
                    set1.color = Color.BLUE //set line and Simple Data color
                    set1.setCircleColor(Color.BLUE)//set point color
                    set1.lineWidth = 3f
                    set1.circleRadius = 4f //set point size
                    set1.setDrawCircleHole(false) //set hole in point
                    set1.valueTextSize = 12f//set text above point size
                    set1.setDrawFilled(true)//set color fill to chart
                    //set1.formLineWidth = 20f
                    //set1.formLineDashEffect = DashPathEffect(floatArrayOf(50f, 5f), 0f)
                    set1.formSize = 15f//set simple data size
                    //set Curve graph
                    set1.mode = LineDataSet.Mode.CUBIC_BEZIER
                    set1.cubicIntensity = 0.2f

                    // create a dataset and give it a type
                    //set1.setFillFormatter(new MyFillFormatter(0f));
                    //set1.setDrawHorizontalHighlightIndicator(false);
                    //set1.setVisible(false);
                    //set1.setCircleHoleColor(Color.WHITE);

                    set2 = LineDataSet(values2, "DIA")
                    set2.setDrawIcons(false)
                    //set1.enableDashedLine(10f, 5f, 0f) //set Dashed line
                    //set1.enableDashedHighlightLine(20f, 5f, 0f)
                    set2.color = Color.CYAN//set line and Simple Data color
                    set2.setCircleColor(Color.CYAN)//set point color
                    set2.lineWidth = 3f
                    set2.circleRadius = 4f //set point size
                    set2.setDrawCircleHole(false) //set hole in point
                    set2.valueTextSize = 12f//set text above point size
                    set2.setDrawFilled(true)//set color fill to chart
                    //set1.formLineWidth = 20f
                    //set1.formLineDashEffect = DashPathEffect(floatArrayOf(50f, 5f), 0f)
                    set2.formSize = 15f//set simple data size
                    //set Curve graph
                    set2.mode = LineDataSet.Mode.CUBIC_BEZIER
                    set2.cubicIntensity = 0.2f



                    //set color shade to chart
                    if (Utils.getSDKInt() >= 18) {
                        val drawSYSgraph = ContextCompat.getDrawable(this, R.drawable.sys_color_line)
                        val drawaDIAgraph = ContextCompat.getDrawable(this, R.drawable.dia_color_line)

                        set1.fillDrawable = drawSYSgraph
                        set2.fillDrawable = drawaDIAgraph
                    } else {
                        set1.fillColor = Color.DKGRAY
                        set2.fillColor = Color.DKGRAY
                    }
                    //set value to data
                    val dataSets = ArrayList<ILineDataSet>()
                    dataSets.add(set1)
                    dataSets.add(set2)
                    val data = LineData(dataSets)
                    mChart!!.data = data
                }
            },
            Response.ErrorListener {
                Toast.makeText(this, "Something went wrong!! $it", Toast.LENGTH_SHORT).show()
                Log.e("eeee","$it")
            })

        val queue = Volley.newRequestQueue(this)
        queue.add(request)


    }


    fun callListBP(){
        val url = "https://ehr-system-project.herokuapp.com/api/examination/list/bloodp/"
        val sharedPreferences = getSharedPreferences("User_Info", Context.MODE_PRIVATE)
        val username =sharedPreferences.getString("Username","Username")

        val request = JsonArrayRequest(
            Request.Method.GET,url + "$username",null,
            Response.Listener {
                for(i in 0 until it.length()){
                    val jsonObj = it.getJSONObject(i)

                    val date = jsonObj.getString("date_add").trim()
                    val time = jsonObj.getString("time_add")
                    val systolic = jsonObj.getString("systolic_blood_pressure_patient")
                    val diastolic = jsonObj.getString("diastolic_blood_pressure_patient")

                    val data = DataBP(date,time,systolic,diastolic)
                    list.add(data)
                    //create adapter
                    history_bp.setHasFixedSize(true)
                    history_bp.layoutManager = LinearLayoutManager(this)
                    val adapter = RvAdapter(this,list)
                    history_bp.adapter = adapter

                }
            },
            Response.ErrorListener {
                Toast.makeText(this, "Something went wrong!! $it", Toast.LENGTH_SHORT).show()
                Log.e("eeee","$it")
            })

        val queue = Volley.newRequestQueue(this)
        queue.add(request)


    }
}




