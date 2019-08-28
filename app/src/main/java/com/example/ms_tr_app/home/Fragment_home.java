package com.example.ms_tr_app.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ms_tr_app.csQRcode.CS_QRcode;
import com.example.ms_tr_app.lsQRcode.LS_QRcode;
import com.example.ms_tr_app.list.List;
import com.example.ms_tr_app.medicine.Medicine;
import com.example.ms_tr_app.R;
import com.example.ms_tr_app.schedule.Schedule;
import com.example.ms_tr_app.schoolbook.SchoolBook;
import com.example.ms_tr_app.schoolbus.SchoolBus;
import com.example.ms_tr_app.studentrecord.studentRecord;

import java.util.Calendar;

public class Fragment_home extends Fragment {

    private static int year, month, day;
    private static TextView tvDateTime;
    private ImageView studentRecordView;
    private ImageView scheduleView;
    private ImageView schoolBookView;
    private ImageView medicineView;
    private ImageView cs_qrcodeView;
    private ImageView ls_qrcodeView;
    private ImageView schoolBusView;
    private ImageView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_home, container, false);


        tvDateTime = view.findViewById(R.id.tvDateTime);
        studentRecordView = view.findViewById(R.id.studentRecordView);
        schoolBookView = view.findViewById(R.id.schoolBookView);
        medicineView = view.findViewById(R.id.medicineView);
        cs_qrcodeView = view.findViewById(R.id.cs_qrcodeView);
        ls_qrcodeView = view.findViewById(R.id.ls_qrcodeView);
        schoolBusView = view.findViewById(R.id.schoolBusView);
        listView = view.findViewById(R.id.listView);
        scheduleView = view.findViewById(R.id.scheduleView);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        //顯示時間
        showRightNow();
        //設定圖片按鈕的點擊事件
        //到離校紀錄
        studentRecordView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), studentRecord.class);
                startActivity(intent);
            }
        });
        //親子聯絡簿
        schoolBookView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SchoolBook.class);
                startActivity(intent);
            }
        });
        //託藥
        medicineView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Medicine.class);
                startActivity(intent);
            }
        });
        //請假
        cs_qrcodeView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CS_QRcode.class);
                startActivity(intent);
            }
        });
        //QRcode掃描
        ls_qrcodeView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LS_QRcode.class);
                startActivity(intent);
            }
        });
        //交通車即時位置
        schoolBusView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SchoolBus.class);
                startActivity(intent);
            }
        });
        //美味菜單
        listView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), List.class);
                startActivity(intent);
            }
        });
        //作息表
        scheduleView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Schedule.class);
                startActivity(intent);
            }
        });


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


}
