package com.example.ms_tr_app.studentrecord;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ms_tr_app.R;
import com.example.ms_tr_app.main.Util;
import com.example.ms_tr_app.task.ImageTask;

public class studentDetail extends AppCompatActivity {
    private final static String TAG = "studentDetail";
    private ImageTask studentImageTask;
    java.text.SimpleDateFormat date = new java.text.SimpleDateFormat("yyyy/MM/dd");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);
        studentRecordVO student = (studentRecordVO) this.getIntent().getSerializableExtra("student");
        if (student == null) {
            Util.showToast(this, "找不到該班幼童通訊錄");
        } else {
            showDetail(student);
        }

    }

    public void showDetail(studentRecordVO student) {
        ImageView imageView = findViewById(R.id.ivStudentDetail);
        String url = Util.URL + "StudentServlet";
        String st_num = student.getSt_num();


        int imageSize = getResources().getDisplayMetrics().widthPixels * 2;
        Bitmap bitmap = null;

        try {
            studentImageTask = new ImageTask(url, st_num, imageSize);
            bitmap = studentImageTask.execute().get();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.drawable.default_image);
        }
        TextView textView = findViewById(R.id.tvStudentDetail);
        String studentInfo =
                "學生姓名：" + student.getSt_name() + "\n"
                        + "學號：" + student.getSt_num() + "\n"
                        + "性別：" + student.getSt_gender() + "\n"
                        + "生日：" + date.format(student.getSt_birthday()) + "\n"
                        + "身分證：" + student.getSt_id() + "\n"
                        + "監護人：" + student.getGd_name() + "\n"
                        + "聯絡電話：" + student.getGd_phone() + "\n"
                        + "戶籍地址：" + student.getSt_r_address() + "\n"
                        + "住址：" + student.getSt_address() + "\n";
//

        textView.setText(studentInfo);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (studentImageTask != null) {
            studentImageTask.cancel(true);
        }
    }
}
