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
                .setMessage("1.ทำการวัดความดันจากอุปกรณ์" +
                        "2.กดปุ่ม เริ่มการเชื่อมต่อข้อมูล ที่แอพพลิเคชั่น \n" +
                        "3.กดปุ่ม + ที่อุปกรณ์วัดความดัน เพื่อเชื่อมต่อกับสมาร์ทโฟน\n" +
                        "จากนั้นจะแสดงชื่ออุปกรณ์ที่พบคือ A6 BT \n" +
                        "4.ปุ่มกด ดึงข้อมูล \n" +
                        "จะแสดงข้อมูลกรวัด\n" +
                        "7.กดปุ่ม บันทึกข้อมูล")
                .setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                });
        return builder.create();
    }
}
