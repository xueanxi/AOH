package com.game.xianxue.ashesofhistory.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.game.xianxue.ashesofhistory.R;
import com.game.xianxue.ashesofhistory.database.PlayerManager;
import com.game.xianxue.ashesofhistory.model.PlayerModel;
import com.game.xianxue.ashesofhistory.model.TeamModel;
import com.game.xianxue.ashesofhistory.service.MainService;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "anxi_MainActivity";

    MainService mService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initService();
        startBattle();

        //PlayerManager.getAllCharacterFromDataBase();

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

    private void startBattle() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                PlayerModel play1 = PlayerManager.getCharacterFromDataBaseByName("关羽");
                PlayerModel play2 = PlayerManager.getCharacterFromDataBaseByName("刘备");
                PlayerModel play3 = PlayerManager.getCharacterFromDataBaseByName("张飞");
                PlayerModel play4 = PlayerManager.getCharacterFromDataBaseByName("马超");
                PlayerModel play5 = PlayerManager.getCharacterFromDataBaseByName("典韦");
                PlayerModel play6 = PlayerManager.getCharacterFromDataBaseByName("许诸");

                play1.setLevel(5);
                play2.setLevel(5);
                play3.setLevel(5);
                play4.setLevel(5);
                play5.setLevel(5);
                play6.setLevel(5);

                ArrayList<PlayerModel> ourPlayer = new ArrayList<PlayerModel>();
                ourPlayer.add(play1);
                ourPlayer.add(play2);
                ourPlayer.add(play3);
                TeamModel t1 = new TeamModel(TeamModel.CAMP_LEFT, ourPlayer);

                ArrayList<PlayerModel> enemyPlayer = new ArrayList<PlayerModel>();
                enemyPlayer.add(play4);
                enemyPlayer.add(play5);
                enemyPlayer.add(play6);
                TeamModel t2 = new TeamModel(TeamModel.CAMP_RIGHT, enemyPlayer);

                if (mService != null) {
                    mService.setTeam(t1, t2);
                    Intent intentStart = new Intent(MainActivity.this, MainService.class);
                    intentStart.setAction(MainService.ACTION_START_BATTLE);
                    startService(intentStart);
                } else {
                    Log.e(TAG, "anxi mService = null !!!");
                }
            }
        }).start();

    }
}
