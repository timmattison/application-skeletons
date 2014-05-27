package com.timmattison.skeletons;

/**
 * Created by timmattison on 5/28/14.
 */
public class BasicRunningMonitor implements RunningMonitor {
    private boolean running = true;

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void stop() {
        this.running = false;

        // Notify all threads that are waiting on us
        this.notifyAll();
    }

    @Override
    public void waitUntilStopping() {
        synchronized (this) {
            try {
                // Wait until notifyAll is called in stop()
                this.wait();
            } catch (InterruptedException e) {
                // We were interrupted, we are probably stopping

                // Make sure we reset the interrupt status flag:
                //   https://stackoverflow.com/questions/5999100/is-there-a-block-until-condition-becomes-true-function-in-java
                Thread.currentThread().interrupt();
            }
        }
    }
}
