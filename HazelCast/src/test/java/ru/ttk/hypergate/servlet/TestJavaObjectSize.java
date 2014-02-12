package ru.ttk.hypergate.servlet;

import org.github.jamm.MemoryMeter;
import org.junit.Test;

/**
 *
 */
public class TestJavaObjectSize {

    @Test
    public void testSizeOneByte(){
        MemoryMeter meter = new MemoryMeter();

        byte b = 1;
        System.out.println("BYTE SIZE:");
        System.out.println( meter.measureDeep(b) );
        System.out.println( meter.measureDeep(b) );
        System.out.println( meter.countChildren(b) );

        boolean _boolean  = true;
        System.out.println("BOOLEAN SIZE:");
        System.out.println( meter.measureDeep(_boolean) );
        System.out.println( meter.measureDeep(_boolean) );
        System.out.println( meter.countChildren(_boolean) );

        short _short  = 1;
        System.out.println("SHORT SIZE:");
        System.out.println( meter.measureDeep(_short) );
        System.out.println( meter.measureDeep(_short) );
        System.out.println( meter.countChildren(_short) );

        int _int  = 2000000;
        System.out.println("INT SIZE:");
        System.out.println( meter.measureDeep(_int) );
        System.out.println( meter.measureDeep(_int) );
        System.out.println( meter.countChildren(_int) );

        double _double  = 2000000.0d;
        System.out.println("DOUBLE SIZE:");
        System.out.println( meter.measureDeep(_double) );
        System.out.println( meter.measureDeep(_double) );
        System.out.println( meter.countChildren(_double) );

        char _char  = 'C';
        System.out.println("CHAR SIZE:");
        System.out.println( meter.measureDeep(_char) );
        System.out.println( meter.measureDeep(_char) );
        System.out.println( meter.countChildren(_char) );

        String _string  = "";
        System.out.println("String SIZE:");
        System.out.println( meter.measureDeep(_string) );
        System.out.println( meter.measureDeep(_string) );
        System.out.println( meter.countChildren(_string) );


        String _string2  = "F";
        System.out.println("String 2 SIZE:");
        System.out.println( meter.measureDeep(_string2) );
        System.out.println( meter.measureDeep(_string2) );
        System.out.println( meter.countChildren(_string2) );
        System.out.println("==" + meter.omitSharedBufferOverhead().measure(_string2));

    }
}
