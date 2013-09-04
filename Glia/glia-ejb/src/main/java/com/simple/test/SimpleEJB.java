package com.simple.test;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import java.io.Serializable;

/**
 */
@Stateless
@Local(ISimpleEJB.class)
public class SimpleEJB implements ISimpleEJB, Serializable {

    @PostConstruct
    public void init(){
        System.out.println("INIT INIT INIT INIT INIT INIT INIT INIT INIT INIT INIT INIT ");
    }

    public String getResult(String value){
        return "added value:" + value;
    }

}
