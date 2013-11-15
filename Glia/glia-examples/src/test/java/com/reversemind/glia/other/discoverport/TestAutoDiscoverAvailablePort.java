package com.reversemind.glia.other.discoverport;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 */
public class TestAutoDiscoverAvailablePort {

    private static final Logger LOG = LoggerFactory.getLogger(TestAutoDiscoverAvailablePort.class);

    @Ignore
    @Test
    public void testFindFreePort() {
        try {

            ServerSocket serverSocket = new ServerSocket(0);
            LOG.debug("serverSocket.isBound():" + serverSocket.isBound());
            LOG.debug("port:" + serverSocket.getLocalPort());

            serverSocket.close();
            LOG.debug("serverSocket.isBound():" + serverSocket.isBound());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
