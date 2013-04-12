package ru.ttk.camel;

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
}

