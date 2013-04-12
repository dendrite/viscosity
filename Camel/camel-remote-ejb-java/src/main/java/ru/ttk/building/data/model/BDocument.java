package ru.ttk.building.data.model;

import ru.ttk.gateway.data.model.GatewayPerson;

import java.util.Date;

/**
 */
public class BDocument extends BObject{

    private static final long serialVersionUID = 100731508440447330L;

    private GatewayPerson author;
    private Date createDate;

    private GatewayPerson modifierPerson;
    private Date modifiedDate;

    public BDocument(){
        this.author = null;
        this.createDate = new Date();

        this.modifierPerson = null;
        this.modifiedDate = new Date();
    }

    public GatewayPerson getAuthor() {
        return author;
    }

    public void setAuthor(GatewayPerson author) {
        this.author = author;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public GatewayPerson getModifierPerson() {
        return modifierPerson;
    }

    public void setModifierPerson(GatewayPerson modifierPerson) {
        this.modifierPerson = modifierPerson;
    }
}
