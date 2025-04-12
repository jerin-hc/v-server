package com.ij3rry.vserver.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class BoundedThreadExecutor {

    public static ExecutorService newBoundedThreadExecutor(int maxConcurrentTask, int timeOutMillSec) {
        Semaphore semaphore = new Semaphore(maxConcurrentTask);

        return Executors.newThreadPerTaskExecutor(task -> Thread.ofVirtual().unstarted(() -> {
            boolean acquired = false;
            try {
                acquired = semaphore.tryAcquire(timeOutMillSec, TimeUnit.MILLISECONDS);
                if (!acquired) {
                    throw new ConnectionTimeOutException("Connection timed out after "+timeOutMillSec+" milliseconds");
                }
                task.run();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ConnectionTimeOutException e) {
                throw new RuntimeException(e);
            } finally {
                if (acquired) {
                    semaphore.release();
                }
            }
        }));
    }

}
