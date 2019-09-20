package com.game.xianxue.aoh.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.game.xianxue.aoh.game.engine.BattleEngine;
import com.game.xianxue.aoh.game.model.TeamModel;

/**
 * Created by user on 5/25/17.
 */
public class MainService extends Service {

    public static final String ACTION_BATTLE = "ACTION_BATTLE";
    public static final String ACTION_ERROR_BATTLE = "ACTION_ERROR_BATTLE";
    public static final String ACTION_START_BATTLE = "ACTION_START_BATTLE";
    public static final String ACTION_PAUSE_BATTLE = "ACTION_PAUSE_BATTLE";
    public static final String ACTION_STOP_BATTLE = "ACTION_STOP_BATTLE";

    TeamModel mT1 = null;   // 第一个队伍
    TeamModel mT2 = null;   // 第二个队伍
    BattleEngine mBattleEngine = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder {
        public MainService getService() {
            return MainService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBattleEngine = BattleEngine.getInstance();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            String action = intent.getAction();
            if (ACTION_START_BATTLE.equals(action)) {
                //startBattle();
            } else if (ACTION_PAUSE_BATTLE.equals(action)) {

            } else if (ACTION_STOP_BATTLE.equals(action)) {

            } else {

            }
        } else if (intent == null) {
            // 服务异常被杀死的情况,intent 为 null

        }
        return START_STICKY;
    }


    public void setTeam(TeamModel t1, TeamModel t2) {
        this.mT1 = t1;
        this.mT2 = t2;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
