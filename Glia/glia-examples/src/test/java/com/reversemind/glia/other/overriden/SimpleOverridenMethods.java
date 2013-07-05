package com.reversemind.glia.other.overriden;

/**
 */
public class SimpleOverridenMethods {

    public String getSimple(String string){
        System.out.println("Argument #1:" + string);
        return "argument #1:" + string;
    }

    public String getSimple(String string, String otherString){
        System.out.println("Argument #1:" + string);
        System.out.println("Argument #2:" + otherString);
        return "argument #1:" + string + " argument #2:" + otherString;
    }

    public String getSimple(int string, String otherString){
        System.out.println("Argument #1:" + string);
        System.out.println("Argument #2:" + otherString);
        return "argument #1:" + string + " argument #2:" + otherString;
    }

}
