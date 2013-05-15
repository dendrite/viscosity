package com.reversemind.glia.server;

/**
 * Date: 5/13/13
 * Time: 4:45 PM
 *
 * @author
 * @since 1.0
 */
public interface IGliaServer {

    /**
     *
     */
    public void start();

    /**
     *
     * @return
     */
    public boolean isRunning();


    /**
     *
     */
    public void shutdown();

    public int getPort();

    public String getName();

    public String getInstanceName();

    public Metrics getMetrics();

    public String getHost();
}
