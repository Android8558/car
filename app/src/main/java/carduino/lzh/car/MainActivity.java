package carduino.lzh.car;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import broadcasts.BluetoothSwitchReceiver;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import customViews.CircleBtnView;
import fragments.Fg_Chat;
import fragments.Fg_Detection;
import fragments.Fg_Gravity;
import fragments.Fg_KeyModle;
import fragments.Fg_Plan;
import fragments.Fg_VoiceRecongize;
import fragments.Fg_connect;
import fragments.Fragment_bluetooth_state;
import interfaces.CarConnected;
import voicereconigze.ActivityTouch;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CarConnected {
    private static final int YUYIN_REQUEST_UI = 1000;
    DrawerLayout drawer;
    /*
  fagments
     */
    FragmentManager fm;
    Fragment_bluetooth_state f_b_s;
    Fg_Gravity fg_gravity;
    Fg_VoiceRecongize fg_voiceRecongize;
    Fg_Chat fg_chat;
    Fg_KeyModle fg_keyModle;
    Fg_Plan fg_plan;
    Fg_connect fg_connect;
    Fg_Detection fg_detection;


    private BlueToothController mController = new BlueToothController();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);

        InitFragments();


    }

    private void InitFragments() {
        fm = getSupportFragmentManager();
        f_b_s = new Fragment_bluetooth_state();
        fg_gravity = new Fg_Gravity();
        fg_voiceRecongize = new Fg_VoiceRecongize();
        fg_chat = new Fg_Chat();
        fg_keyModle = new Fg_KeyModle();
        fg_plan = new Fg_Plan();
        fg_connect = new Fg_connect();
        fg_detection = new Fg_Detection();
        fm.beginTransaction().replace(R.id.main_contain_relativeLayout, fg_connect).commitAllowingStateLoss();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            moveTaskToBack(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_enable:
                //打开蓝牙
                if (!mController.isturnOn()) ;
                mController.turnOnBlueTooth();
                break;
            case R.id.action_close:
                //打开蓝牙
                if (mController.isturnOn()) ;
                mController.disableBluetooth();

                break;
            case R.id.action_connect:
                fm.beginTransaction().replace(R.id.main_contain_relativeLayout, fg_connect).commitAllowingStateLoss();

                break;
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, DeviceInfoActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_key) {
            fm.beginTransaction().replace(R.id.main_contain_relativeLayout, fg_keyModle).commitAllowingStateLoss();
        } else if (id == R.id.nav_gravity) {
            fm.beginTransaction().replace(R.id.main_contain_relativeLayout, fg_gravity).commitAllowingStateLoss();

        } else if (id == R.id.nav_voice) {
            fm.beginTransaction().replace(R.id.main_contain_relativeLayout, fg_voiceRecongize).commitAllowingStateLoss();


        } else if (id == R.id.nav_chat) {
            fm.beginTransaction().replace(R.id.main_contain_relativeLayout, fg_chat).commitAllowingStateLoss();

        } else if (id == R.id.nav_share) {
            showShare();
        } else if (id == R.id.nav_check) {
            fm.beginTransaction().replace(R.id.main_contain_relativeLayout, fg_detection).commitAllowingStateLoss();

        } else if (id == R.id.nav_plan) {
            fm.beginTransaction().replace(R.id.main_contain_relativeLayout, fg_plan).commitAllowingStateLoss();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    private void showShare() {
        View rootView = getWindow().getDecorView();
        rootView.setDrawingCacheEnabled(true);
        rootView.buildDrawingCache();
        Bitmap bitmap = rootView.getDrawingCache();
        if (bitmap != null) {
            try {
                FileOutputStream outputStream = new FileOutputStream(getCacheDir() + "/temp.jpg");
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();


            }
            ShareSDK.initSDK(this);
            OnekeyShare oks = new OnekeyShare();
            //关闭sso授权
            oks.disableSSOWhenAuthorize();


            oks.setTitle("遥控小车");
            oks.setTitleUrl("http://fir.im/zpul");
            oks.setText("遥控小车，实时遥控小车运动！");
            oks.setImagePath(getCacheDir() + "/temp.jpg");
            oks.setSiteUrl("http://fir.im/zpul");
            oks.setSite(getString(R.string.app_name));

            oks.show(this);
        }

    }

    @Override
    public void connceted() {
        drawer.openDrawer(Gravity.LEFT);
    }
}