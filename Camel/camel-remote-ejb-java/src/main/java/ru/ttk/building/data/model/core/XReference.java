package ru.ttk.building.data.model.core;

import ru.ttk.building.data.model.BDocument;

import java.io.Serializable;
import java.net.URL;


public class XReference<T extends BDocument> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String objectType;
    private String objectId;
    private String name;
    private XReferenceTypes refType;
    private T object;

    public XReference(T obj) {
        objectType = obj.getClass().getCanonicalName();
        objectId = obj.getId();
        name = obj.getDisplayName();

        if ( objectType.length() > 255){
            objectType = objectType.substring(0,254);
        }

        if ( objectId.length() > 255){
            objectId = objectId.substring(0,254);
        }

        if ( name.length() > 255){
            name = name.substring(0,254);
        }

        object = obj;
        refType = XReferenceTypes.OBJECT;
    }

    public XReference(XReferenceTypes xReferenceTypes) {

    }

    public XReference() {

    }

    public XReference(String displayName) {
        name = displayName;
        refType = XReferenceTypes.STRING;
    }

    public XReference(URL url) {
        name = url.toString();
        refType = XReferenceTypes.URL;
    }




















    public final String getObjectType() {
        return objectType;
    }

    public void setObjectId(String objectId){
        this.objectId = objectId;
    }

    public String getObjectId() {
        return objectId;
    }

    public final String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public boolean equals(Object obj) {
        if (objectId.equals(((XReference<?>) obj).getObjectId()) && objectType.equals(((XReference<?>) obj).getObjectType())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        return "objectType:" + this.objectType + " objectId:" + this.objectId + " name:" + this.name + " ";
    }

    public String assignDisplayName(){
        return this.name;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public XReferenceTypes getRefType() {
        return refType;
    }

    public void setRefType(XReferenceTypes refType) {
        this.refType = refType;
    }
}
