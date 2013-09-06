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
    public String functionNumber1(String parameter1, String parameter2) {
        this.delay();
        return "FN #1 - summ par1:" + parameter1 + " par2:" + parameter2;
    }

    @Override
    public String functionNumber2(String parameter1, String parameter2) {
        this.delay(1000);
        return "FN #2 - summ par1:" + parameter1 + " par2:" + parameter2;
    }

    @Override
    public String functionNumber3(String parameter1, String parameter2) {
        this.delay();
        return "FN #3 - summ par1:" + parameter1 + " par2:" + parameter2;
    }

    @Override
    public String functionNumber4(String parameter1, String parameter2) {
        this.delay();
        return "FN #4 - summ par1:" + parameter1 + " par2:" + parameter2;
    }

    @Override
    public String functionNumber5(String parameter1, String parameter2) {
        this.delay();
        return "FN #5 - summ par1:" + parameter1 + " par2:" + parameter2;
    }

    private void delay() {
//        try {
//            Thread.sleep(Math.round(1000 * Math.random()));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void delay(long delayTime) {
        try {
            Thread.sleep(delayTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
