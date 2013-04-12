package ru.ttk.building.data.model.building;


import ru.ttk.building.data.model.BDocument;


/**
 *
 */
public class XManagementCompanyContact extends BDocument {

    private String fio;
    private String contactInfo;
    private String comments;
    private XManagementCompany managementCompany;

    public XManagementCompanyContact(){
        super();
        this.fio = "";
        this.contactInfo = "";
        this.comments = "";
    }

    public XManagementCompany getManagementCompany() {
        return managementCompany;
    }

    public void setManagementCompany(XManagementCompany managementCompany) {
        this.managementCompany = managementCompany;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}
