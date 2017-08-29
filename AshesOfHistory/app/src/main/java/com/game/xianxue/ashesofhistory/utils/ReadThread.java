package com.game.xianxue.ashesofhistory.utils;

public class ReadThread implements Runnable{
    public Thread t;
    private String threadName;
    boolean suspended=false;

    public ReadThread(String threadName){
        this.threadName=threadName;
        System.out.println("Creating " +  threadName );
    }

    public void run() {
        for(int i = 10; i > 0; i--) {
            System.out.println("Thread: " + threadName + ", " + i);
            // Let the thread sleep for a while.
            try {
                Thread.sleep(300);
                synchronized(this) {
                    while(suspended) {
                        wait();
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Thread " +  threadName + " interrupted.");
                e.printStackTrace();
            }
            System.out.println("Thread " +  threadName + " exiting.");
        }
    }

    /**
     * 开始
     */
    public void start(){
        System.out.println("Starting " +  threadName );
        if(t==null){
            t=new Thread(this, threadName);
            t.start();
        }
    }

    /**
     * 暂停
     */
    void suspend(){
        suspended = true;
    }

    /**
     * 继续
     */
    synchronized void resume(){
        suspended = false;
        notify();
    }
}