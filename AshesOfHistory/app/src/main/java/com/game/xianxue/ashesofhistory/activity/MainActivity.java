package com.game.xianxue.ashesofhistory.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.game.xianxue.ashesofhistory.Log.SimpleLog;
import com.game.xianxue.ashesofhistory.R;
import com.game.xianxue.ashesofhistory.database.BasePersonManager;
import com.game.xianxue.ashesofhistory.game.engine.BattleEngine;
import com.game.xianxue.ashesofhistory.game.skill.SkillBase;
import com.game.xianxue.ashesofhistory.model.TeamModel;
import com.game.xianxue.ashesofhistory.model.person.BasePerson;
import com.game.xianxue.ashesofhistory.model.person.BattlePerson;
import com.game.xianxue.ashesofhistory.model.person.NormalPerson;
import com.game.xianxue.ashesofhistory.service.MainService;
import com.game.xianxue.ashesofhistory.game.engine.SuspendThread;
import com.game.xianxue.ashesofhistory.utils.XmlUtils;

import java.util.ArrayList;

import static com.game.xianxue.ashesofhistory.utils.XmlUtils.getAllSkill;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "anxi_MainActivity";

    MainService mService = null;
    boolean isStart = false;
    boolean isPause = false;

    SuspendThread run = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermission();



    }

    /**
     * 申请sd卡权限
     */
    private void requestPermission() {
        // 获取访问sd卡的权限
        int permissionCheck1 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //int permissionCheck3 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);

        boolean isHasPermission = true;
        if (permissionCheck1 != PackageManager.PERMISSION_GRANTED) {
            isHasPermission = false;
        }
        if (permissionCheck2 != PackageManager.PERMISSION_GRANTED) {
            isHasPermission = false;
        }
            /*if (permissionCheck3 != PackageManager.PERMISSION_GRANTED) {
                isHasPermission = false;
            }*/
        if (!isHasPermission) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            //Manifest.permission.READ_CONTACTS,
                    }, 100);
        }
    }

    private void initService() {
        Intent intent = new Intent(MainActivity.this, MainService.class);
        startService(intent);
        bindService(intent, mSc, 0);
    }

    ServiceConnection mSc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = ((MainService.MyBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (run != null) {
            run.stop();
        }

    }

    private void startBattle() {
    }
}
