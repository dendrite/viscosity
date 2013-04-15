package com.test.synthproxy.shared;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 4/15/13
 * Time: 10:52 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public class PClient implements Serializable {

    private String firstName;
    private String lastName;
    private List<PAddress> list;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<PAddress> getList() {
        return list;
    }

    public void setList(List<PAddress> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PClient{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", list=" + list +
                '}';
    }
}
