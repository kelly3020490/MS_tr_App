package com.example.ms_tr_app.studentrecord;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ms_tr_app.R;
import com.example.ms_tr_app.cs._Class;
import com.example.ms_tr_app.main.Util;
import com.example.ms_tr_app.task.ClassTask;
import com.example.ms_tr_app.task.CommonTask;
import com.example.ms_tr_app.task.ImageTask;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class studentRecord extends AppCompatActivity {
    private static final String TAG = "studentRecord";
    private RecyclerView rvStudents;
    private CommonTask getStudentTask;
    private ImageTask studentImageTask;
    private ClassTask classTask;
    private Spinner spClass;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_record);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        spClass = findViewById(R.id.spClass);
        rvStudents = findViewById(R.id.rvStudents);
        rvStudents.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Util.networkConnected(this)) {
            classTask = new ClassTask();
            try {
                List<_Class> classList = classTask.execute().get();
                List<String> cs = new ArrayList<>();
                for (int i = 0; i < classList.size(); i++) {
                    cs.add(classList.get(i).getCs_name());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cs);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spClass.setAdapter(adapter);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            //交給控制器的請求參數
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "getAll");
            String jsonOut = jsonObject.toString();
            updateUI(jsonOut);
        } else {
            Util.showToast(this, R.string.msg_NoNetwork);
        }
    }

    public void onSearch(View view) {
        // spinner position starts from 0

        String c = "C00" + (spClass.getSelectedItemPosition() + 1);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "findByClass");
        jsonObject.addProperty("cs_num", c);
        String jsonOut = jsonObject.toString();
        updateUI(jsonOut);
    }

    private void updateUI(String jsonOut) {
        getStudentTask = new CommonTask(Util.URL + "StudentServlet", jsonOut);
        List<studentRecordVO> studentList = null;
        try {
            String jsonIn = getStudentTask.execute().get();
            Type listType = new TypeToken<List<studentRecordVO>>() {
            }.getType();
            studentList = new GsonBuilder().setDateFormat("yyyy-MM-dd").create().fromJson(jsonIn, listType);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        if (studentList == null || studentList.isEmpty()) {
            Util.showToast(this, "找不到該班幼童通訊錄");
        } else {
            rvStudents.setAdapter(new StudentListAdapter(this, studentList));
        }
    }

    private class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.MyViewHolder> {
        private Context context;
        private LayoutInflater layoutInflater;
        private List<studentRecordVO> studentList;
        private int imageSize;

        StudentListAdapter(Context context, List<studentRecordVO> studentList) {
            this.context = context;
            layoutInflater = LayoutInflater.from(context);
            this.studentList = studentList;

            imageSize = getResources().getDisplayMetrics().widthPixels / 4;

        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView ivPicture;
            TextView tvName, tvSt_num,tvGd_name;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                ivPicture = itemView.findViewById(R.id.ivPicture);
                tvName = itemView.findViewById(R.id.tvName);
                tvSt_num = itemView.findViewById(R.id.tvSt_num);
                tvGd_name = itemView.findViewById(R.id.tvGd_name);
            }
        }

        public int getItemCount(){return studentList.size();}

        public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
            View itemView = layoutInflater.inflate(R.layout.cardview_student,parent,false);
            return new MyViewHolder(itemView);
        }

        public void onBindViewHolder(MyViewHolder holder,int position){
            final studentRecordVO student = studentList.get(position);
            String url = Util.URL + "StudentServlet";
            String st_num = student.getSt_num();
            studentImageTask = new ImageTask(url,st_num,imageSize,holder.ivPicture);
            studentImageTask.execute();

            holder.tvName.setText("姓名：".concat(student.getSt_name()));
            holder.tvSt_num.setText("學號：".concat(student.getSt_num()));
            holder.tvGd_name.setText("監護人：".concat(student.getGd_name()));
            holder.itemView.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view){
                    Intent intent = new Intent(studentRecord.this,studentDetail.class);
                    intent.putExtra("student",student);
                    startActivity(intent);
                }
            });


        }


    }
    protected  void onStop(){
        super.onStop();
        if(getStudentTask != null){
            getStudentTask.cancel(true);
        }

        if (studentImageTask != null){
            studentImageTask.cancel(true);
        }
        if(classTask != null){
            classTask.cancel(true);
        }

    }


}
