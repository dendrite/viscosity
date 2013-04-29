package com.reversemind.glia.other.future;

import java.io.Serializable;
import java.util.concurrent.*;

/**
 * Date: 4/26/13
 * Time: 11:15 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public class FutureTasksPlusCustomExecutor_Test4 implements Serializable {

    public static void main(String... args) {

        try{

            ExecutorService executor = new ThreadPoolExecutor(0, 1,
                                                                1L, TimeUnit.SECONDS,
                                                                new SynchronousQueue<Runnable>());

            FutureTask<String> future = new FutureTask<String>(
                    new Callable<String>() {
                        public String call() {
                            long bT = System.currentTimeMillis();
                            try {

                                Thread.sleep(10000);

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return " delta:" + (System.currentTimeMillis() - bT);
                        }
                    });

            executor.submit(future);
            executor.shutdown();

            try {
                executor.awaitTermination(2, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                System.out.println("Time is UP!!!!!!");
            }


            if(future.isCancelled()){
                System.out.println("FUTURE is Canceled");
            }

            if(future.isDone()){
                System.out.println("GEt some values from Future:" + future.get());
            }


        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

}
