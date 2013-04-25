package com.reversemind.glia.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 *
 */
public class FutureTaskExample {

    static class FirstProcess<String> implements Callable<String> {

        @Override
        public String call() throws Exception {
            Thread.sleep(1000 * 2);
            return (String) "1000 * 5";
        }

    }

    public static void main(String... args){
        ExecutorService executor = Executors.newFixedThreadPool(2);
        FutureTask<String> futureOne = new FutureTask<String>(
                new Callable<String>() {

                    @Override
                    public String call() throws Exception {
                        System.out.println("run task");
                        Thread.sleep(1000 * 5);
                        System.out.println("Task Done");
                        return (String) "1000 * 2";
                    }

                }
        );
        executor.execute(futureOne);
        System.out.println("Execute task");
        while (!futureOne.isDone()) {
            try {
                // I wait until both processes are finished.
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }

}
