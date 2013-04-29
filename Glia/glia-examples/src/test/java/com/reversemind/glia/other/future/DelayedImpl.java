package com.reversemind.glia.other.future;

import java.util.concurrent.Delayed;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class DelayedImpl<T> implements Delayed {

    private Future<T> task;
    private final long maxExecTimeMinutes = 1;//MAX_THREAD_LIFE_MINUTES;
    private final long startInMillis = System.currentTimeMillis();

    private DelayedImpl(Future<T> task) {
        this.task = task;
    }

    public long getDelay(TimeUnit unit) {
        return unit.convert((startInMillis + maxExecTimeMinutes*60*1000) - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    public int compareTo(Delayed o) {
        Long thisDelay = getDelay(TimeUnit.MILLISECONDS);
        Long thatDelay = o.getDelay(TimeUnit.MILLISECONDS);
        return thisDelay.compareTo(thatDelay);
    }

    public Future<T> getTask() {
        return task;
    }
}
