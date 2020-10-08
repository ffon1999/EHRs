package com.example.ehrs

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.home.*
import kotlinx.android.synthetic.main.temp_bt.*

class BT_Temp  : AppCompatActivity() {

    private var button: Button? = null
    var expandableViewTemp : LinearLayout? = null
    var startSyncTemp : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.temp_bt)

        // how to sync btn
        button = findViewById(R.id.info_bt_temp)

        expandableViewTemp = findViewById(R.id.expandableViewBTTemp)
        startSyncTemp = findViewById(R.id.start_sync_temp)


        start_sync_temp.setOnClickListener(View.OnClickListener {
            if (expandableViewBTTemp.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(expandableViewTemp, AutoTransition())
                expandableViewBTTemp.setVisibility(View.VISIBLE)
            } else {
                TransitionManager.beginDelayedTransition(expandableViewTemp, AutoTransition())
                expandableViewBTTemp.setVisibility(View.GONE)
            }
        })

        // press how to sync BT btn
        info_bt_temp.setOnClickListener(View.OnClickListener { openDialog() })
    }

    fun openDialog() {
        val infoTempdialog = HowToSyncTemp()
        infoTempdialog.show(supportFragmentManager, "how to sync temperature dialog")
    }
}