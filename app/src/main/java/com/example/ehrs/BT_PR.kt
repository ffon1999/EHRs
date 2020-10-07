package com.example.ehrs

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.bp_bt.*
import kotlinx.android.synthetic.main.pr_bt.*

class BT_PR  : AppCompatActivity() {

    private var button: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pr_bt)
        button = findViewById(R.id.info_bt_pr)
        info_bt_pr.setOnClickListener(View.OnClickListener { openDialog() })
    }

    fun openDialog() {
        val infoPRdialog = HowToSyncPR()
        infoPRdialog.show(supportFragmentManager, "how to sync blood pressure dialog")
    }
}