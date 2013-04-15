package com.test.synthproxy.shared;

import java.io.Serializable;

/**
 *
 */
public interface IPCity extends Serializable {
    public String createString(String str);
    public PClient getClientForAddress(String streetName);
}
