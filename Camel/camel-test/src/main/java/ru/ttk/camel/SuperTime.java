package ru.ttk.camel;

import org.codehaus.jackson.map.ObjectMapper;

/**
 */
public  class SuperTime {

    static long prevTime = 0;

    public String time(){
        long nowTime = System.currentTimeMillis();
        String out = "";
        if(prevTime == 0){
            out = "now:" + nowTime;
        }else{
            out = "now:" + nowTime + " delta:" + (nowTime - prevTime) + " ms";
        }
        prevTime = nowTime;

        return out;
    }

    public void process(SamplePojo samplePojo){
        System.out.println("processed SamplePojo:" + samplePojo);
    }

    public String emitJson() throws Exception{
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(new SamplePojo("name", 101));
    }
}

