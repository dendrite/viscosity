package ru.ttk.gateway.data.model;

import ru.ttk.gateway.service.IGatewayEntity;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * This entity is like DTO an Entity
 *
 */
public class GatewayEntity implements IGatewayEntity, Serializable {

    protected String id;
    protected String fullClassName;
    protected String displayName;
    protected String dataSourceName;

    public GatewayEntity() {
        id = UUID.randomUUID().toString();
    }


    public GatewayEntity(String id, String fullClassName, String displayName, String dataSourceName){
        this.id = id;
        this.fullClassName = fullClassName;
        this.displayName = displayName;
        this.dataSourceName = dataSourceName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPortableFullClassName() {
        return fullClassName;
    }

    public void setPortableFullClassName(String fullClassName) {
        this.fullClassName = fullClassName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    @Override
    public String toString(){
        return " id:" + this.id + " displayName:" + this.displayName + " fullClassName:" + this.fullClassName + " dataSourceName:" + this.dataSourceName;
    }

}
