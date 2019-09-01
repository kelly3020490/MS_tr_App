package com.example.ms_tr_app.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ms_tr_app.R;
import com.example.ms_tr_app.csQRcode.CS_QRcode;
import com.example.ms_tr_app.list.List;
import com.example.ms_tr_app.lsQRcode.LS_QRcode;
import com.example.ms_tr_app.main.TrImageTask;
import com.example.ms_tr_app.main.TrMember;
import com.example.ms_tr_app.main.Util;
import com.example.ms_tr_app.medicine.Medicine;
import com.example.ms_tr_app.schedule.Schedule;
import com.example.ms_tr_app.schoolbook.SchoolBook;
import com.example.ms_tr_app.schoolbus.SchoolBus;
import com.example.ms_tr_app.studentrecord.studentRecord;
import com.example.ms_tr_app.task.CommonTask;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Calendar;

public class Fragment_home extends Fragment {
    public static final String TAG = "Fragment_home";

    private static int year, month, day;
    private static TextView tvDateTime, tr_job, tr_name;
    private ImageView tr_pic;
    private ImageView studentRecordView;
    private ImageView scheduleView;
    private ImageView schoolBookView;
    private ImageView medicineView;
    private ImageView cs_qrcodeView;
    private ImageView ls_qrcodeView;
    private ImageView schoolBusView;
    private ImageView listView;
    private CommonTask showTrInfoTask;
    private TrImageTask teacherImageTask;

    String t_num;
    TrMember trMember;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_home, container, false);


        tvDateTime = view.findViewById(R.id.tvDateTime);
        tr_job = view.findViewById(R.id.tr_job);
        tr_name = view.findViewById(R.id.tr_name);
        tr_pic = view.findViewById(R.id.tr_pic);
        studentRecordView = view.findViewById(R.id.studentRecordView);
        schoolBookView = view.findViewById(R.id.schoolBookView);
        medicineView = view.findViewById(R.id.medicineView);
        cs_qrcodeView = view.findViewById(R.id.cs_qrcodeView);
        ls_qrcodeView = view.findViewById(R.id.ls_qrcodeView);
        schoolBusView = view.findViewById(R.id.schoolBusView);
        listView = view.findViewById(R.id.listView);
        scheduleView = view.findViewById(R.id.scheduleView);

        //取得登入頁面傳送過來的老師編號
        t_num = getActivity().getIntent().getExtras().getString("t_num");

//        tr_name.setText(t_name);


        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {


        super.onActivityCreated(savedInstanceState);

        //顯示時間
        showRightNow();

        //顯示老師姓名，職稱，照片
        showTrInfo(t_num);

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
                Bundle bundle = new Bundle();
                bundle.putString("t_num",t_num);
                intent.putExtras(bundle);
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

    TrMember showTrInfo(String t_num) {
        int imageSize = getResources().getDisplayMetrics().widthPixels * 2;
        ;

//        boolean showTrInfo = false;
        //判斷是否取得連線
        if (Util.networkConnected(getActivity())) {
            String url = Util.URL + "TrMemberServlet";
            //new一個JsonObject物件
            JsonObject jsonObject = new JsonObject();
            //將屬性和值放入jsonObject物件內
            jsonObject.addProperty("action", "findById");
            jsonObject.addProperty("t_num", t_num);
            //將jsonObject物件轉成字串
            String jsonOut = jsonObject.toString();
            showTrInfoTask = new CommonTask(url, jsonOut);
            Log.e(TAG, "將字串包成json送出");


            try {
                String result = showTrInfoTask.execute().get();
//                 showTrInfo = Boolean.valueOf(result);
                Log.e(TAG, "進入try");
                //gson再接日期資料時，如果需要轉換格式需要利用以下方式
                Gson gson = new Gson();
                JsonObject jsonObject2 = gson.fromJson(result, JsonObject.class);
                trMember = gson.fromJson(jsonObject2.get("trMember").getAsString(), TrMember.class);

                tr_name.setText(trMember.getT_name());
                Log.e(TAG, "成功傳入老師姓名:" + trMember.getT_name());

                tr_job.setText("職稱：" + trMember.getT_job());
                Log.e(TAG, "成功傳入老師職稱:" + trMember.getT_job());

                teacherImageTask = new TrImageTask(url, t_num, imageSize, tr_pic);
                teacherImageTask.execute();
                Log.e(TAG, "成功傳入照片");

            } catch (Exception e) {
                Log.e(TAG, e.toString());
//                showTrInfo = false;
                Log.e(TAG, "進入Exception");
            }
        } else {
            Util.showToast(getActivity(), R.string.msg_NoNetwork);
            Log.e(TAG, "沒有連線");
        }
        return trMember;
    }

    public void onStop() {
        super.onStop();
        if (showTrInfoTask != null) {
            showTrInfoTask.cancel(true);
        }

        if (teacherImageTask != null) {
            teacherImageTask.cancel(true);
        }
        
    }
}



