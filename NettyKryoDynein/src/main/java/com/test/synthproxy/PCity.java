package com.test.synthproxy;

/**
 *
 */
public class PCity implements IPCity{

    @Override
    public String createString(String str) {
        return "" + System.currentTimeMillis() + "  + " + str;
    }
}
