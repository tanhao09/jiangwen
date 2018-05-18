package com.jiangwen.example.concurrency;

import com.jiangwen.annotion.NotThreadSafe;
import org.apache.log4j.Logger;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;



/**
 * Created by ymind on 2018/5/15.
 */
@NotThreadSafe
public class ConcurrencyTest {
    private static  final Logger logger=Logger.getLogger(ConcurrencyTest.class);
    public static int clientTotal=5000;
    public static int threadtotal =200;
    public static int count =0;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadtotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0 ; i<clientTotal ; i++){
            executorService.execute(() -> {
                try{
                    semaphore.acquire();
                    add();
                    semaphore.release();
                }catch (Exception e){
                    e.printStackTrace();
                    logger.error("exception {}",e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        logger.error("count:{}"+count);
    }

    private static void add(){
        count++;
    }



}
