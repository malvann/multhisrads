package com.example.multhithradingconcurencyparfomance.image_repaint_ex;

public class MinMaxMetrics {
    private volatile long max;
    private volatile long min;

    /**
     * Initializes all member variables
     */
    public MinMaxMetrics() {
        max = Long.MIN_VALUE;
        min = Long.MAX_VALUE;
    }

    /**
     * Adds a new sample to our metrics.
     */
    public synchronized void addSample(long newSample) {
        max = Math.max(max, newSample);
        min = Math.min(min, newSample);
    }

    /**
     * Returns the smallest sample we've seen so far.
     */
    public long getMin() {
        return min;
    }

    /**
     * Returns the biggest sample we've seen so far.
     */
    public long getMax() {
        return max;
    }
}
