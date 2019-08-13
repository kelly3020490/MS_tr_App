package com.example.ms_tr_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class Home extends AppCompatActivity {
    private static TextView tvDateTime;
    private static int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tvDateTime = findViewById(R.id.tvDateTime);

        showRightNow();
    }

    private static void showRightNow() {
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        updateInfo();
    }

    private static void updateInfo() {
        tvDateTime.setText(new StringBuilder().append(year)
                .append("-").append(parseNum(month + 1)).append("-").append(parseNum(day)));
    }

    private static String parseNum(int day) {
        if (day >= 10) {
            return String.valueOf(day);
        } else {
            return "0" + String.valueOf(day);
        }
    }
    public void onstudentRecord(View view){
        Intent intent = new Intent(this,studentRecord.class);
        startActivity(intent);

    }
    public void onschoolbook(View view){
        Intent intent = new Intent(this,SchoolBook.class);
        startActivity(intent);

    }
    public void onmedicine(View view){
        Intent intent = new Intent(this,Medicine.class);
        startActivity(intent);

    }
    public void onschoolbus(View view){
        Intent intent = new Intent(this,SchoolBus.class);
        startActivity(intent);

    }
    public void oncs_qrcode(View view){
        Intent intent = new Intent(this,CS_QRcode.class);
        startActivity(intent);

    }
    public void onls_qrcode(View view){
        Intent intent = new Intent(this,LS_QRcode.class);
        startActivity(intent);

    }
    public void onlist(View view){
        Intent intent = new Intent(this,List.class);
        startActivity(intent);

    }
    public void onschedule(View view){
        Intent intent = new Intent(this,Schedule.class);
        startActivity(intent);

    }
}
