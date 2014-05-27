package com.timmattison.skeletons;

/**
 * Created by timmattison on 5/28/14.
 */
public interface RunningMonitor {
    /**
     * Returns whether or not we believe we should be running right now
     *
     * @return
     */
    public boolean isRunning();

    /**
     * Indicate that we are stopping
     */
    public void stop();

    /**
     * Waits until the monitor believes we are stopping
     */
    public void waitUntilStopping();
}
