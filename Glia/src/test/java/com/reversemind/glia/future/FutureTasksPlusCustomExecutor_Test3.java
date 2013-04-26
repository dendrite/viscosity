package com.reversemind.glia.future;

import java.io.Serializable;
import java.util.concurrent.*;

/**
 * Date: 4/26/13
 * Time: 11:04 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public class FutureTasksPlusCustomExecutor_Test3 implements Serializable {

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

            long timBegin = System.currentTimeMillis();
            long howLong = 0;

            while (!future.isDone()) {
                howLong = (System.currentTimeMillis() - timBegin);
                try {
                    // I wait until both processes are finished.
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

//            try {
//                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
//            } catch (InterruptedException e) {
//                // interrupted
//            }

            System.out.println("GET VALUE FROM FUTURE:" + future.get());

        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

}
