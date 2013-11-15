package com.test.timer;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 */
public class TimerTest {

    private static final Logger LOG = LoggerFactory.getLogger(TimerTest.class);

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
            LOG.info("\n\n " + count++ + " - " + new Date());
        }
    }

}
