package ru.ttk.camel;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

/**
 *
 */
public class SamplePojoTest {

    @Test
    public void testGetName() throws Exception {
        SamplePojo samplePojo = new SamplePojo("name", 101);

        System.out.println("SamplePojo:" + samplePojo);


        ObjectMapper mapper = new ObjectMapper();

        System.out.println(mapper.writeValueAsString(samplePojo));


    }
}
