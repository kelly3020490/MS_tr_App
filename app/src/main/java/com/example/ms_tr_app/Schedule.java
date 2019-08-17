package com.example.ms_tr_app;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Schedule extends AppCompatActivity {
    CalendarView calendarview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        calendarview = findViewById(R.id.schedule);
        calendarview.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(Schedule.this, "你選擇的時間是：：" + year + "年" + (month+1) + "月" + dayOfMonth + "日", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
