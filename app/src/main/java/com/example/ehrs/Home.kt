package com.example.ehrs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import kotlinx.android.synthetic.main.home.*

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

    var BtnAccount : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)


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

        BtnAccount = findViewById(R.id.btn_account)


        //press btn to expandable cardview
        cardview_bp.setOnClickListener(View.OnClickListener {
            if (expandableViewBP.getVisibility() == View.GONE) {
                //TransitionManager.beginDelayedTransition(cvBtn,new AutoTransition());
                expandableViewBP.setVisibility(View.VISIBLE)
            } else {
                //TransitionManager.beginDelayedTransition(cvBtn,new AutoTransition());
                expandableViewBP.setVisibility(View.GONE)
            }
        })

        cardview_pr.setOnClickListener(View.OnClickListener {
            if (expandableViewPR.getVisibility() == View.GONE) {
                //TransitionManager.beginDelayedTransition(cvBtn,new AutoTransition());
                expandableViewPR.setVisibility(View.VISIBLE)
            } else {
                //TransitionManager.beginDelayedTransition(cvBtn,new AutoTransition());
                expandableViewPR.setVisibility(View.GONE)
            }
        })

        cardview_temp.setOnClickListener(View.OnClickListener {
            if (expandableViewTemp.getVisibility() == View.GONE) {
                //TransitionManager.beginDelayedTransition(cvBtn,new AutoTransition());
                expandableViewTemp.setVisibility(View.VISIBLE)
            } else {
                //TransitionManager.beginDelayedTransition(cvBtn,new AutoTransition());
                expandableViewTemp.setVisibility(View.GONE)
            }
        })

        cardview_weight.setOnClickListener(View.OnClickListener {
            if (expandableViewWeight.getVisibility() == View.GONE) {
                //TransitionManager.beginDelayedTransition(cvBtn,new AutoTransition());
                expandableViewWeight.setVisibility(View.VISIBLE)
            } else {
                //TransitionManager.beginDelayedTransition(cvBtn,new AutoTransition());
                expandableViewWeight.setVisibility(View.GONE)
            }
        })

        cardview_height.setOnClickListener(View.OnClickListener {
            if (expandableViewHeight.getVisibility() == View.GONE) {
                //TransitionManager.beginDelayedTransition(cvBtn,new AutoTransition());
                expandableViewHeight.setVisibility(View.VISIBLE)
            } else {
                //TransitionManager.beginDelayedTransition(cvBtn,new AutoTransition());
                expandableViewHeight.setVisibility(View.GONE)
            }
        })

        cardview_glucose.setOnClickListener(View.OnClickListener {
            if (expandableViewGlucose.getVisibility() == View.GONE) {
                //TransitionManager.beginDelayedTransition(cvBtn,new AutoTransition());
                expandableViewGlucose.setVisibility(View.VISIBLE)
            } else {
                //TransitionManager.beginDelayedTransition(cvBtn,new AutoTransition());
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

        //press btn to synce data with bluetooth
        btn_addBT_BP.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(this@Home, BT_BP::class.java)
            )
        })
        btn_addBT_PR.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(this@Home, com.example.ehrs.BT_PR::class.java)
            )
        })

        //press btn goto profile page
        btn_account.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(this@Home, Profile_Account::class.java)
            )
        })

    }

}