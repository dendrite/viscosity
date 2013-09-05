package ejb.server.service;

import ejb.shared.IServiceSimple;

import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 */
@Stateless
@Local(IServiceSimple.class)
public class ServiceSimple implements IServiceSimple {

    @Override
    public String functionNumberOne(String parameter1, String parameter2) {
        return "functionNumberOne - summ par1:" + parameter1 + " par2:" + parameter2;
    }

    @Override
    public String functionNumberTwo(String parameter1, int parameter2) {
        return "functionNumberTwo - summ par1:" + parameter1 + " par2*10:" + parameter2*10;
    }
}
