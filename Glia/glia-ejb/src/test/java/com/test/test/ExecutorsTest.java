package com.test.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 *
 */
public class ExecutorsTest {


    public static void main(String... args) throws ExecutionException, InterruptedException {

        StringCallable sc1 = new StringCallable("123");
        StringCallable sc2 = new StringCallable("456");
        StringCallable sc3 = new StringCallable("678");
        StringCallable sc4 = new StringCallable("90");


        List<StringCallable> callableList = new ArrayList<StringCallable>();
        for(int i=0; i<5; i++){
            callableList.add(new StringCallable(i + "" + i + "" + i));
        }


        List<FutureTask<String>> futureTaskList = new ArrayList<FutureTask<String>>();
        for(StringCallable stringCallable: callableList){
            futureTaskList.add(new FutureTask<String>(stringCallable));
        }


        long beginTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(futureTaskList.size());
        for(FutureTask<String> futureTask: futureTaskList){
            executor.execute(futureTask);
        }


        boolean ready = false;

        while(!ready){

            int count = 0;
            for(FutureTask<String> futureTask: futureTaskList){
                if(futureTask.isDone()){
                    System.out.println("Value:" + futureTask.get());
                    count++;
                }
            }

            if(count == futureTaskList.size()){
                ready = true;
            }

        }
        System.out.println("All DONE for:" + (System.currentTimeMillis()-beginTime));

        executor.shutdown();
    }

    /**
     *
     */


}
