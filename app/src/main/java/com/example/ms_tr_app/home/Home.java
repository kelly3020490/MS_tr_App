package com.example.ms_tr_app.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.ms_tr_app.R;
import com.example.ms_tr_app.info.Info;
import com.example.ms_tr_app.main.MainActivity;
import com.example.ms_tr_app.main.Util;
import com.example.ms_tr_app.more.More;
import com.example.ms_tr_app.schoolcalendar.School_Calendar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {


    private ViewPager viewPager;//這個viewpager就是接fragment傳來的物件
    private BottomNavigationView navigation;
    //底部導航欄物件
    private List<Fragment> listFragment;

    //注意這實作的是BottomNavigationView的點擊監聽器   點選底部項目的點擊事件
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_info:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_school_calendar:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_more:
                    viewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        changePage();//換頁方法
    }


    //------放fragment用的方法--------------------------------------------------------------------
    private void changePage() {
        viewPager = findViewById(R.id.viewpager);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //會預先載入三頁 避免跳頁時有檔案遺失
        viewPager.setOffscreenPageLimit(3);
        //向ViewPager新增各頁面
        listFragment = new ArrayList<>();
        listFragment.add(new Fragment_home());
        listFragment.add(new Info());
        listFragment.add(new School_Calendar());
        listFragment.add(new More());


        MyFragAdapter myAdapter = new MyFragAdapter(getSupportFragmentManager(), this, listFragment);
        viewPager.setAdapter(myAdapter);
        //導航欄點選事件和ViewPager滑動事件,讓兩個控制元件相互關聯
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //這裡設定為:當點選到某子項,ViewPager就滑動到對應位置
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.navigation_info:
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.navigation_school_calendar:
                        viewPager.setCurrentItem(2);
                        return true;
                    case R.id.navigation_more:
                        viewPager.setCurrentItem(3);
                        return true;

                    default:
                        break;
                }
                return false;
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                // 當滑動到某一位置,導航欄對應位置被按下
                navigation.getMenu().getItem(position).setChecked(true);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class MyFragAdapter extends FragmentPagerAdapter {
        private Context context;
        private List<Fragment> listFragment;

        public MyFragAdapter(FragmentManager fm, Context context, List<Fragment> listFragment) {
            super(fm);
            this.context = context;
            this.listFragment = listFragment;
        }

        @Override
        public Fragment getItem(int position) {
            return listFragment.get(position);
        }

        @Override
        public int getCount() {
            return listFragment.size();
        }


    }

    //----放fragment用的方法-----------------------------------------------------------------
    @Override
    //捕捉返回鍵
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            ConfirmExit();//按返回鍵，則執行退出確認
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void ConfirmExit() {//退出確認
        AlertDialog.Builder ad = new AlertDialog.Builder(Home.this);
        ad.setTitle("登出");
        ad.setMessage("確定要登出嗎?");
        ad.setPositiveButton("是", new DialogInterface.OnClickListener() {//退出按鈕
            public void onClick(DialogInterface dialog, int i) {
                SharedPreferences preferences = getSharedPreferences(Util.PREF_FILE, MODE_PRIVATE);
                preferences.edit().remove("login").remove("t_num").remove("t_password").apply();
                Intent intent = new Intent(Home.this, MainActivity.class);
                startActivity(intent);
                finish();//關閉activity
            }
        });
        ad.setNegativeButton("否", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                //不退出不用執行任何操作
            }
        });
        ad.show();//顯示對話框
    }

}
