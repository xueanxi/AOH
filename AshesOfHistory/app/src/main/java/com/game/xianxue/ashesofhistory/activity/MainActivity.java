package com.game.xianxue.ashesofhistory.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.game.xianxue.ashesofhistory.R;
import com.game.xianxue.ashesofhistory.service.MainService;
import com.game.xianxue.ashesofhistory.utils.ReadThread;

import static com.game.xianxue.ashesofhistory.R.id.bt_test_thread;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "anxi_MainActivity";

    MainService mService = null;
    boolean isStart = false;
    boolean isPause = false;

    ReadThread run = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        run = new ReadThread("thread_anxi");

        Button bt = (Button) this.findViewById(R.id.bt_test_thread);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStart){
                    if(isPause){
                        run.resume();
                        isPause = false;
                    }else{
                        run.suspend();
                        isPause = true;
                    }
                }else{
                    run.start();
                    isStart = true;
                }
            }
        });

        //initService();
        //startBattle();

        //BasePersonManager.getAllPersonFromDataBase();

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
        if (mSc != null) {
            unbindService(mSc);
            mService = null;
        }

    }

    private void startBattle() {
    }
}
