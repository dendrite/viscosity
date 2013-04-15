package com.test.synthproxy.shared;

import java.io.Serializable;

/**
 * Date: 4/15/13
 * Time: 10:54 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public class PAddress implements Serializable {

    private String street;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "PAddress{" +
                "street='" + street + '\'' +
                '}';
    }
}
