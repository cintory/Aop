package com.cintory.aop;

/**
 * 作者：Cintory on 2018/6/25 18:01
 * 邮箱：Cintory@gmail.com
 */
public class StopWatch {

    private long startTime;
    private long endTime;
    private long elapsedTime;


    public StopWatch() {}


    private void reset() {
        startTime = 0;
        endTime = 0;
        elapsedTime = 0;
    }


    public void start() {
        reset();
        startTime = System.nanoTime();
    }


    public void stop() {
        if (startTime != 0) {
            endTime = System.nanoTime();
            elapsedTime = endTime - startTime;
        }
        else {
            reset();
        }
    }


    public long getElapsedTime() {
        return elapsedTime;
    }
}
