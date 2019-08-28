package com.example.ms_tr_app.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ms_tr_app.R;

import java.util.ArrayList;
import java.util.List;

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

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final List<ScheduleVO> scheduleList = new ArrayList<>();
        scheduleList.add(new ScheduleVO(824,"早班","07:30 ~ 17:00"));

        recyclerView.setAdapter(new ScheduleAdapter(scheduleList));

    }


        private class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
            private List<ScheduleVO> scheduleList;

            private ScheduleAdapter(List<ScheduleVO> scheduleList) {
                this.scheduleList = scheduleList;
            }

            class ViewHolder extends RecyclerView.ViewHolder {
                private TextView date;
                private TextView leave;
                private TextView leave_time;



                private ViewHolder(View view) {
                    super(view);
                    date = view.findViewById(R.id.date);
                    leave = view.findViewById(R.id.leave);
                    leave_time = view.findViewById(R.id.leave_time);

                }
            }
            @Override
            public int getItemCount() {
                return scheduleList.size();
            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_schedule, parent, false);
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                //將資料注入到View裡
                final ScheduleVO schedule = scheduleList.get(position);
                holder.date.setText(schedule.getDate().toString());
                holder.leave.setText(schedule.getLeave());
                holder.leave_time.setText(schedule.getLeave_time());


            }
        }



}
