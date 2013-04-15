package com.test.synthproxy.remote;

import com.test.synthproxy.shared.IPCity;
import com.test.synthproxy.shared.PAddress;
import com.test.synthproxy.shared.PClient;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class CityServer implements IPCity{

    @Override
    public String createString(String str) {
        return "SERVER: + " + str;
    }

    @Override
    public PClient getClientForAddress(String streetName) {

        System.out.println("Getting client for address");
        System.out.println();


        PAddress pAddress = new PAddress();
        pAddress.setStreet(streetName);

        PAddress pAddress2 = new PAddress();
        pAddress2.setStreet(streetName + " second street");

        PClient client = new PClient();
        client.setFirstName("FIRST NAME");
        client.setLastName("LAST NAME");

        List<PAddress> list = new ArrayList<PAddress>();
        list.add(pAddress);
        list.add(pAddress2);
        client.setList(list);

        return client;
    }
}
