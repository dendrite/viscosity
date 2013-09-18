package com.test.timer;

import org.junit.Test;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 */
public class TimerTest {

    @Test
    public void testTimer() throws InterruptedException {

        TimerTask timerTask = new MetricsUpdateTask();

        Timer timer;
        timer = new Timer();
        timer.schedule(timerTask, 10000, 10000);


        Thread.sleep(30000);

    }

    private class MetricsUpdateTask extends TimerTask {

        long count = 0;

        @Override
        public void run() {
            System.out.println("\n\n " + count++ + " - " + new Date());
        }
    }

}
