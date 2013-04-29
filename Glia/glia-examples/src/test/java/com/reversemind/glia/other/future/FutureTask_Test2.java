package com.reversemind.glia.other.future;

import java.io.Serializable;
import java.util.concurrent.*;

/**
 * Date: 4/26/13
 * Time: 10:43 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public class FutureTask_Test2 implements Serializable {

    public static final long TIME_OUT_FOR_PAYLOAD = 2000; // sec

    public static void main(String... args) throws ExecutionException, InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(1);

        FutureTask<String> future = new FutureTask<String>(
                new Callable<String>() {
                    public String call() {
                        long bT = System.currentTimeMillis();
                        try {

                            Thread.sleep(3000);

                        } catch (InterruptedException e) {
                            //e.printStackTrace();
                        }
                        return " delta:" + (System.currentTimeMillis() - bT);
                    }
                });


        executor.execute(future);

        String vvv = "EMPTY";
        try {
            vvv = future.get(4, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            System.out.println("TimeoutException == HERE");
            future.cancel(true);
            e.printStackTrace();
        }

        System.out.println("VVV:" + vvv);

        long timBegin = System.currentTimeMillis();
        try{
            while (!future.isDone()) {
                if((System.currentTimeMillis() - timBegin)>=TIME_OUT_FOR_PAYLOAD){
                    future.cancel(true);
                    executor.shutdown();
                    System.out.println("DELTA TIME IS:" + (System.currentTimeMillis() - timBegin));
                    throw new InterruptedException("TIME IS UP for GETTING PAYLOAD");
                }
            }
        }catch(InterruptedException ex){
            ex.printStackTrace();
        }


        if(future.isCancelled()){
            System.out.println("Task was canceled");
            executor.shutdown();
        }

        if(future.isDone()){
            System.out.println("Get from source:" + future.get());
            executor.shutdown();
        }

    }

}
