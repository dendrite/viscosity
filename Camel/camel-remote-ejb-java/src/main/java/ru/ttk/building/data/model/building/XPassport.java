package ru.ttk.building.data.model.building;

import java.io.Serializable;
import java.util.Date;


public class XPassport implements Serializable {

    private String number;
    private String serialNumber;
    private Date issueDate;
    private String issuer;

    public XPassport(){
        this.number = "";
        this.serialNumber = "";
        this.issueDate = new Date();
        this.issuer = "";
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    @Override
    public String toString(){
        return " " + this.number + ":" + this.serialNumber;
    }

}
