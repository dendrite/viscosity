package com.reversemind.glia.future;

import junit.framework.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.util.concurrent.*;

/**
 * Date: 4/26/13
 * Time: 11:51 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public class FutureTask_TimeOut_Example implements Serializable {

    @Test
    public void testFutureTaskTimeOutExample(){

        final long EXECUTOR_TIME_OUT = 1000;    // 1sec
        final long FUTURE_TASK_TIME_OUT = 2000; // 2 sec

        ExecutorService executor = new ThreadPoolExecutor(0, 1,
                                                            EXECUTOR_TIME_OUT, TimeUnit.MILLISECONDS,
                                                            new SynchronousQueue<Runnable>());

        FutureTask<String> future = new FutureTask<String>(
                new Callable<String>() {
                    public String call() {
                        long bT = System.currentTimeMillis();
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                        }
                        return " delta time:" + (System.currentTimeMillis() - bT);
                    }
                });

        executor.execute(future);

        String resultFromFutureTask = "EMPTY";
        try {
            resultFromFutureTask = future.get(FUTURE_TASK_TIME_OUT, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            System.out.println("TimeoutException == HERE");
            future.cancel(true);
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException == HERE");
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println("ExecutionException == HERE");
            e.printStackTrace();
        }

        System.out.println("resultFromFutureTask:" + resultFromFutureTask);
        Assert.assertEquals("EMPTY",resultFromFutureTask);

        if(future.isCancelled()){
            System.out.println("Task was canceled");
            executor.shutdown();
        }

        if(future.isDone()){
            executor.shutdown();
        }

        if(!executor.isShutdown()){
            executor.shutdown();
        }

    }

}
