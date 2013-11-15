package com.reversemind.glia.other;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 *
 */
public class HostName {

    private static final Logger LOG = LoggerFactory.getLogger(HostName.class);

    public static void main(String... args) throws UnknownHostException, SocketException {
        String localhostname = java.net.InetAddress.getLocalHost().getHostName();
        LOG.debug("HOST NAME:" + localhostname);
        LOG.debug("" + InetAddress.getLocalHost().getAddress());
        LOG.debug(InetAddress.getLocalHost().getHostAddress());

        byte[] bytes = InetAddress.getLocalHost().getAddress();
        for (int i = 0; i < bytes.length; i++) {
            LOG.debug("" + bytes[i]);
        }

        InetAddress localHost = InetAddress.getLocalHost();

        printInetAddress("localHost", localHost);

        String hostName = localHost.getHostName();
        String canonicalHostName = localHost.getCanonicalHostName();
        printByName("  by" + hostName, hostName);
        printByName("  by" + canonicalHostName, canonicalHostName);

        LOG.debug("Full list of Network Interfaces:");
        Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
        if (en == null) {
            LOG.debug("got null from NetworkInterface.getNetworkInterfaces()");
        } else for (int networkInterfaceNumber = 0; en.hasMoreElements(); networkInterfaceNumber++) {
            NetworkInterface intf = en.nextElement();

            LOG.debug("");
            String ifaceId = "networkInterface[" + networkInterfaceNumber + "]";
            LOG.debug("  " + ifaceId + ".setName: " + intf.getName());
            LOG.debug("  " + ifaceId + ".displayName: " + intf.getDisplayName());

            Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
            for (int addressNumber = 0; enumIpAddr.hasMoreElements(); addressNumber++) {
                InetAddress ipAddr = enumIpAddr.nextElement();
                LOG.debug("");
                printInetAddress("    " + ifaceId + ".address[" + addressNumber + "]", ipAddr);
            }
        }


    }

    private static void printByName(String prefix, String canonicalHostName)
            throws UnknownHostException {
        LOG.debug("");
        InetAddress[] allMyIps = InetAddress.getAllByName(canonicalHostName);
        for (int i = 0; i < allMyIps.length; i++) {
            String subPrefix = prefix + "[" + i + "]";
            LOG.debug(subPrefix);
            LOG.debug("");
            InetAddress myAddress = allMyIps[i];
            printInetAddress("  " + subPrefix, myAddress);
        }
    }

    private static void printInetAddress(String prefix, InetAddress myAddress) {
        LOG.debug(prefix + ".toString: " + myAddress);
        LOG.debug(prefix + ".hostName: " + myAddress.getHostName());
        LOG.debug(prefix + ".canonicalHostName: " + myAddress.getCanonicalHostName());
        LOG.debug(prefix + ".getHostAddress: " + myAddress.getHostAddress());
    }
}
