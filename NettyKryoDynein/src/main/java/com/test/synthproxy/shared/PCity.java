package com.test.synthproxy.shared;

import com.test.synthproxy.shared.IPCity;
import com.test.synthproxy.shared.PClient;

/**
 *
 */
public class PCity implements IPCity {

    @Override
    public String createString(String str) {
        return "" + System.currentTimeMillis() + "  + " + str;
    }

    @Override
    public PClient getClientForAddress(String streetName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
