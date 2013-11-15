package com.reversemind.glia.other.future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 *
 */
public class FutureTaskExample {

    private static final Logger LOG = LoggerFactory.getLogger(FutureTaskExample.class);

    static class FirstProcess<String> implements Callable<String> {

        @Override
        public String call() throws Exception {
            Thread.sleep(1000 * 2);
            return (String) "1000 * 5";
        }

    }

    public static void main(String... args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        FutureTask<String> futureOne = new FutureTask<String>(
                new Callable<String>() {

                    @Override
                    public String call() throws Exception {
                        LOG.debug("start task");
                        Thread.sleep(1000 * 5);
                        LOG.debug("Task Done");
                        return (String) "1000 * 2";
                    }

                }
        );
        executor.execute(futureOne);
        LOG.debug("Execute task");
        while (!futureOne.isDone()) {
            try {
                // I wait until both processes are finished.
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LOG.error("", e);
            }
        }

        LOG.debug("FUTURE GET:" + futureOne.get());
        executor.shutdown();
    }

}
