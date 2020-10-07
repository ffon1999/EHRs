package com.example.ehrs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

public class HowToSyncBP extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle SavedTnstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("วิธีการเชื่อมต่อข้อมูลด้วยบลูทูธ")
                .setMessage("1.ทำการวัดความดัน หากวัดเสร็จแล้ว กดปุ่ม OK" +
                        "เพื่อบันทึกข้อมูลที่เครื่อง\n" +
                        "2.กดปุ่มบลูทูธที่เครื่องวัด\n" +
                        "3.เปิดบลูทูธที่มือถือ\n" +
                        "4.เลือกอุปกรณ์วัดความดัน\n" +
                        "   HEM-7361T\n" +
                        "5.กด OK เพื่อเข้าถึงข้อมูล\n" +
                        "6.รอแสดงข้อมูลการวัด\n" +
                        "7.กดบันทึกข้อมูล")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                });
        return builder.create();
    }
}
