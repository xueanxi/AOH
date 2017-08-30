package com.game.xianxue.ashesofhistory.game.engine;

/**
 * 可以暂停运行的线程模型
 */
public abstract class SuspendThread implements Runnable {

    public Thread innerThread;      // 内部线程
    boolean suspended = false;        // 是否暂停，ture为暂停，false为不暂停
    boolean isContinue = true;      // 线程是否继续运行，true 继续，false 线程结束。
    private int intervalTime = 100; // 线程循环的间隔事件

    public SuspendThread() {
        suspended = false;
    }

    @Override
    public void run() {
        while (isContinue) {

            // 使用同步锁还有wait机制，判断是否需要暂停
            synchronized (this) {
                try {
                    while (suspended) {
                        wait();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 外部执行的主逻辑
            this.runPersonelLogic();

            // 循环执行的间隔时间
            if(intervalTime != 0) {
                try {
                    Thread.sleep(intervalTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public abstract void runPersonelLogic();

    /**
     * 开始启动
     *
     * @param intervalTime 线程循环的时间间隔
     */
    public void start(int intervalTime) {
        if (innerThread == null) {
            innerThread = new Thread(this);
            this.intervalTime = intervalTime;
            innerThread.start();
        }
    }

    /**
     * 停止运行，停止之后就结束了，不能恢复运行
     * 如果要恢复的话，执行 suspend暂停方法，suspend暂停之后可以调用 resume 方法恢复运行
     */
    public void stop() {
        isContinue = false;
    }

    /**
     * 暂停
     */
    public void suspend() {
        suspended = true;
    }

    /**
     * 继续
     */
    public synchronized void resume() {
        suspended = false;
        notify();
    }
}