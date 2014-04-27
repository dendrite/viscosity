package ru.ttk.camel;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

/**
 *
 */
public class OtherPojoTest {

    @Test
    public void testEnum() throws IOException {
        OtherPojo otherPojo = new OtherPojo("name", OtherPojo.Type.VALUE1);
        System.out.println("POJO:" + otherPojo);



        ObjectMapper mapper = new ObjectMapper();

        System.out.println("JSON:" + mapper.writeValueAsString(otherPojo));


        System.out.println("\n\nConvert JSON to POJO");

        String JSON = "{\"name\":\"name\",\"type\":1}";

        OtherPojo _otherPojo = mapper.readValue(JSON, OtherPojo.class);
        System.out.println("CONVERTED BACK POJO:" + _otherPojo);
        System.out.println(_otherPojo.getType().equals(OtherPojo.Type.VALUE1));


    }

}
