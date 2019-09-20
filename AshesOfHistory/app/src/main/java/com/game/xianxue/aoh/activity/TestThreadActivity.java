package com.game.xianxue.aoh.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.game.xianxue.aoh.R;


public class TestThreadActivity extends AppCompatActivity {
    private static final String TAG = "TestThreadActivity";
    MyThread thread = null;
    private static final Object lock = new Object();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_thread);

        thread = new MyThread();

        Button buttonStart = (Button) this.findViewById(R.id.bt_start_thread_);
        Button buttonStop = (Button) this.findViewById(R.id.bt_stop_thread);
        Button buttonResume = (Button) this.findViewById(R.id.bt_resume_thread);
        Button buttonPause = (Button) this.findViewById(R.id.bt_pause_thread);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread.start();
            }
        });
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread.stopThread();
            }
        });
        buttonResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread.resumeThread();
            }
        });
        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread.pauseThread();
            }
        });



    }

    static class  MyThread extends Thread{
        @Override
        public void run() {
            isPause = false;
            isStop = false;

            while (!isStop){
                Log.d(TAG,"run "+System.currentTimeMillis());

                // 一个人行动之后，暂停，等待战斗引擎唤醒
/*                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }*/

                SystemClock.sleep(500);

                checkIfPause();

                Log.d(TAG,"run2 "+System.currentTimeMillis());

                SystemClock.sleep(500);

                checkIfPause();
            }
        }


        public boolean isStop = false;
        public boolean isPause = false;

        private void checkIfPause(){
            synchronized (lock) {
                try {
                    while (isPause) {
                        lock.wait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void resumeThread(){
            synchronized (lock) {
                isPause = false;
                lock.notify();
            }
        }

        public void pauseThread(){
            isPause = true;
        }

        public void stopThread(){
            isStop = true;
        }
    }

}
