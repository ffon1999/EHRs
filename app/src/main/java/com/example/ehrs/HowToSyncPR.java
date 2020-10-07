package com.example.ehrs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

public class HowToSyncPR extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle SavedTnstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("วิธีการเชื่อมต่อข้อมูลด้วยบลูทูธ")
                .setMessage("1.เปิดบลูทูธในโทรศัพท์มือถือ \n " +
                            "2.เปิดเครื่องวัดอุณหภูมิ    \n " +
                            "3.ทำการวัดอุณหภูมิ     \n " +
                            "4.รอสถานะข้อมูลการวัด แสดงข้อมูล \n "  +
                            "5.กดปุ่มบันทึกข้อมูล     \n " )
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                });
        return builder.create();
    }
}
