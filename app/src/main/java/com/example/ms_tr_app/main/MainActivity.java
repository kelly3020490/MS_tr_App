package com.example.ms_tr_app.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;

import com.example.ms_tr_app.R;
import com.example.ms_tr_app.home.Home;
import com.example.ms_tr_app.task.CommonTask;
import com.google.gson.JsonObject;


public class MainActivity extends AppCompatActivity {
    public static final int STARTUP_DELAY = 200;
    public static final int ANIM_ITEM_DURATION = 1500;
    public static final int EDITTEXT_DELAY = 300;
    public static final int BUTTON_DELAY = 500;
    public static final int VIEW_DELAY = 400;
    public static final String TAG = "MainActivity";
    public CommonTask isTrMemberTask;
    public static String trMember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView logoImageView = findViewById(R.id.meisin_Logo);
        ViewGroup container = findViewById(R.id.container);
        setResult(RESULT_CANCELED);

        ViewCompat.animate(logoImageView)
                .translationY(-330)
                .setStartDelay(STARTUP_DELAY)
                .setDuration(ANIM_ITEM_DURATION)
                .setInterpolator(new DecelerateInterpolator(1.2f))
                .start();

        for (int i = 0; i < container.getChildCount(); i++) {
            View view = container.getChildAt(i);
            ViewPropertyAnimatorCompat viewAnimator;

            if (view instanceof EditText) {
                viewAnimator = ViewCompat.animate(view)
                        .scaleY(1).scaleX(1)
                        .setStartDelay((EDITTEXT_DELAY * i) + 500)
                        .setDuration(500);
            } else if (view instanceof Button) {
                viewAnimator = ViewCompat.animate(view)
                        .scaleY(1).scaleX(1)
                        .setStartDelay((BUTTON_DELAY * i) + 500)
                        .setDuration(500);
            } else {
                viewAnimator = ViewCompat.animate(view)
                        .translationY(100).alpha(1)
                        .setStartDelay((VIEW_DELAY * i) + 500)
                        .setDuration(1000);
            }

            viewAnimator.setInterpolator(new DecelerateInterpolator()).start();
        }

    }
    protected void onStart() {
        super.onStart();
        //設定存取權限  MODE_PRIVATE  只允許本應用程式內存取
        SharedPreferences preferences = getSharedPreferences(Util.PREF_FILE,
                MODE_PRIVATE);
        boolean login = preferences.getBoolean("login", false);
        if (login) {
            //讀取偏好設定檔的資料
            String t_num = preferences.getString("t_num", "");
            String t_password = preferences.getString("t_password", "");
            if (isTrMember(t_num,t_password)) {
                setResult(RESULT_OK);



            }
        }
    }
   //透過帳號和密碼判斷是否為老師
    private boolean isTrMember(final String t_num, final String t_password) {
        boolean isTrMember = false;
        //判斷是否取得連線
        if (Util.networkConnected(this)) {
            String url = Util.URL + "TrMemberServlet";
            //new一個JsonObject物件
            JsonObject jsonObject = new JsonObject();
            //將屬性和值放入jsonObject物件內
            jsonObject.addProperty("action", "isTrMember");
            jsonObject.addProperty("t_num", t_num);
            jsonObject.addProperty("t_password", t_password);
            //將jsonObject物件轉成字串
            String jsonOut = jsonObject.toString();
            isTrMemberTask = new CommonTask(url, jsonOut);
            Log.e(TAG,"將字串包成json送出");
            try {
                Log.e(TAG,"進入try");
                String result = isTrMemberTask.execute().get();
//                isTrMember = Boolean.valueOf(result);

                Log.e(TAG,"result = " + result);
                if(result.equals("")){
                    Log.e(TAG,"沒有抓到老師資料");
                }else{
                    isTrMember = true;
                    trMember = result;
                    Log.e(TAG,"收到的老師姓名為：" + trMember);
                }
            } catch (Exception e) {
                Log.e(TAG, e.toString());
                isTrMember = false;
                Log.e(TAG,"進入Exception");
            }
        } else {
            Util.showToast(this, R.string.msg_NoNetwork);
            Log.e(TAG,"沒有連線");
        }
        return isTrMember;
    }



    public void onLogin(View view) {
        EditText etname = findViewById(R.id.etname);
        EditText etpsd = findViewById(R.id.etpsd);
        String t_num = etname.getText().toString().trim();
        String t_password = etpsd.getText().toString().trim();
        if(t_num.length() <= 0 || t_password.length() <= 0){
            Toast.makeText(this,"請輸入帳號和密碼",Toast.LENGTH_LONG).show();
            return;
        }
        if (isTrMember(t_num, t_password)) {
            SharedPreferences preferences = getSharedPreferences(
                    Util.PREF_FILE, MODE_PRIVATE);
            //將資料存入偏好設定檔
            preferences.edit().putBoolean("login", true)
                    .putString("t_num", t_num)
                    .putString("t_password", t_password).apply();
            setResult(RESULT_OK);

            Intent intent = new Intent(this, Home.class);
            Bundle bundle = new Bundle();
            bundle.putString("t_num",t_num);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this,"帳號或密碼錯誤，請重新輸入",Toast.LENGTH_LONG).show();

        }


    }

//    @Override
//    //捕捉返回鍵
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if((keyCode == KeyEvent.KEYCODE_BACK)){
//            ConfirmExit();//按返回鍵，則執行退出確認
//            return  true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    public void ConfirmExit(){//退出確認
//        AlertDialog.Builder ad=new AlertDialog.Builder(MainActivity.this);
//        ad.setTitle("登出");
//        ad.setMessage("確定要登出嗎?");
//        ad.setPositiveButton("是", new DialogInterface.OnClickListener() {//退出按鈕
//            public void onClick(DialogInterface dialog, int i) {
//                // TODO Auto-generated method stub
//                MainActivity.this.finish();//關閉activity
//            }
//        });
//        ad.setNegativeButton("否",new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int i) {
//                //不退出不用執行任何操作
//            }
//        });
//        ad.show();//顯示對話框
//    }
//
    protected void onStop() {
        super.onStop();
        if (isTrMemberTask != null) {
            isTrMemberTask.cancel(true);
        }
    }
}
