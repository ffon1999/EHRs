package com.example.ehrs

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.bp_bt.*

class BT_BP : AppCompatActivity() {
    private var button: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bp_bt)
        button = findViewById(R.id.info_bt_bp)
        info_bt_bp.setOnClickListener(View.OnClickListener { openDialog() })
    }

    fun openDialog() {
        val infoBPdialog = HowToSyncBP()
        infoBPdialog.show(supportFragmentManager, "how to sync blood pressure dialog")
    }
}