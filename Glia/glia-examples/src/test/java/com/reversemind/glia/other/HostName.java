package com.reversemind.glia.other;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 */
public class HostName {

    public static void main(String... args) throws UnknownHostException {
        String localhostname = java.net.InetAddress.getLocalHost().getHostName();
        System.out.println("HOST NAME:" + localhostname);
        System.out.println(InetAddress.getLocalHost().getAddress());
        System.out.println(InetAddress.getLocalHost().getHostAddress());

        byte[] bytes = InetAddress.getLocalHost().getAddress();
        for(int i=0; i<bytes.length;i++){
            System.out.println(bytes[i]);
        }
    }

}
