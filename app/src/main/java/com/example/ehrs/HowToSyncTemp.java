package com.example.ehrs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

public class HowToSyncTemp extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle SavedTnstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("วิธีการเชื่อมต่อข้อมูลด้วยบลูทูธ")
                .setMessage("1.กดปุ่ม เริ่มการเชื่อมต่อข้อมูล \n " +
                            "2.เปิดเครื่องวัดอุณหภูมิ    \n " +
                            "3.ทำการวัดอุณหภูมิ     \n " +
                            "4.รอซักครู่ จนมีการแสดงข้อมูลการวัดอุณหภูมิในแอพพลิเคชั่น \n "  +
                            "5.กดปุ่มบันทึกข้อมูล     \n " )
                .setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                });
        return builder.create();
    }
}
