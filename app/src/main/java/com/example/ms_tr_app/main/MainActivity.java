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
        SharedPreferences preferences = getSharedPreferences(Util.PREF_FILE,
                MODE_PRIVATE);
        boolean login = preferences.getBoolean("login", false);
        if (login) {
            String t_num = preferences.getString("t_num", "");
            String t_password = preferences.getString("t_password", "");
            if (isTrMember(t_num,t_password)) {
                setResult(RESULT_OK);

            }
        }
    }

    private boolean isTrMember(final String t_num, final String t_password) {
        boolean isTrMember = false;
        if (Util.networkConnected(this)) {
            String url = Util.URL + "TrMemberServlet";
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "isTrMember");
            jsonObject.addProperty("t_num", t_num);
            jsonObject.addProperty("t_password", t_password);
            String jsonOut = jsonObject.toString();
            isTrMemberTask = new CommonTask(url, jsonOut);
            Log.e(TAG,"將字串包成json送出");
            try {
                String result = isTrMemberTask.execute().get();
                isTrMember = Boolean.valueOf(result);
                Log.e(TAG,"進入try");
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
            preferences.edit().putBoolean("login", true)
                    .putString("t_num", t_num)
                    .putString("t_password", t_password).apply();
            setResult(RESULT_OK);
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this,"帳號或密碼錯誤，請重新輸入",Toast.LENGTH_LONG).show();

        }


    }

    protected void onStop() {
        super.onStop();
        if (isTrMemberTask != null) {
            isTrMemberTask.cancel(true);
        }
    }
}
