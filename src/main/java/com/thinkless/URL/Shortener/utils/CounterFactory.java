package com.thinkless.URL.Shortener.utils;

import com.thinkless.URL.Shortener.model.Counter;

public class CounterFactory {
    private static final int MAX_RANGE = 100000;

    public static Counter defaultCounter(int nodeId) {
        return new Counter(nodeId,
                0,
                MAX_RANGE,
                0,
                true);
    }

    public static Counter createCounterFromMaxCounterRange(int nodeId, Counter maxRangeCounter) {
        int nextStart = maxRangeCounter.getCounterEnd() + 1;
        return new Counter(nodeId,
                nextStart,
                nextStart + MAX_RANGE,
                nextStart,
                true);
    }

}
