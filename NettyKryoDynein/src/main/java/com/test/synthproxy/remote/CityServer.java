package com.test.synthproxy.remote;

import com.test.synthproxy.shared.IPCity;

/**
 *
 */
public class CityServer implements IPCity{

    @Override
    public String createString(String str) {
        return "SERVER: + " + str;
    }
}
