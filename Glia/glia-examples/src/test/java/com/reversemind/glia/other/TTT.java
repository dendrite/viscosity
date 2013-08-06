package com.reversemind.glia.other;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 7/30/13
 * Time: 6:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class TTT {

    @Test
    public void testSorting(){

        System.out.println("extracted number2:" + extractNumber2("вл.15 стр.3FIAS "));

        String number = "";
        String house = null;

        if(number == null) number = "";
        if(house == null) house = "";

        System.out.println( "equals number:" + number + " house:" + house + "|" + !(number != null ? !number.equals(house) : house != null) );

        System.out.println("Number empty:" + something(number,house));

    }

    private boolean something(String s1, String s2){
        return StringUtils.isEmpty(s1);
    }

    private int extractNumber2(String string){
        int value = 0;
        System.out.println(string.replaceAll("[^0-9]",""));
        try{
            value = Integer.parseInt(string.replaceAll("[^\\d]",""));
        }catch (Exception ex){
        }finally {
            return value;
        }
    }
}
