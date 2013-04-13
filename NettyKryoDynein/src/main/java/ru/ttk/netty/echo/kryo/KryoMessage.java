package ru.ttk.netty.echo.kryo;

import java.io.Serializable;

/**
 *
 */
public class KryoMessage implements Serializable {

    private String applicationName = "PassportHouse";
    private String className = "ru.ttk.house.model.XBuilding";

    private Object object;

    private String status; // need make it more

    public KryoMessage(){

    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString(){
        return "applicationName:" + applicationName + " className:" + className + "\n object:"+ object +" \nstatus:" + this.status;
    }

}
