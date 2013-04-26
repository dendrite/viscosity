package com.reversemind.glia.discoverport;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 */
public class TestAutoDiscoverAvailablePort {

    @Ignore
    @Test
    public void testFindFreePort() {
        try {

            ServerSocket serverSocket = new ServerSocket(0);
            System.out.println("serverSocket.isBound():" + serverSocket.isBound());
            System.out.println("port:" + serverSocket.getLocalPort());

            serverSocket.close();
            System.out.println("serverSocket.isBound():" + serverSocket.isBound());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
