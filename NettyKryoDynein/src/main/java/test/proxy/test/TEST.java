package test.proxy.test;

import test.proxy.PStreet;
import test.proxy.local.City;
import test.proxy.local.House;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TEST {


    public static void main(String[] argv){

        City city = new City();
        city.setId("1");
        city.setName("Москва");

        PStreet pStreet = new PStreet();
        pStreet.setId("123");

        House house = new House();
        house.setName("Дом.1");

        List<House> list = new ArrayList<House>();
        list.add(house);

        city.setHouses(list);
         city.setStreet(pStreet);


        System.out.println(city);
        System.out.println(city.getStreet());

    }

}
