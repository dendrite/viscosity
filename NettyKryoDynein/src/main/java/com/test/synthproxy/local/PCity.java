package com.test.synthproxy.local;

import com.test.synthproxy.shared.IPCity;

/**
 *
 */
public class PCity implements IPCity {

    @Override
    public String createString(String str) {
        return "" + System.currentTimeMillis() + "  + " + str;
    }
}
