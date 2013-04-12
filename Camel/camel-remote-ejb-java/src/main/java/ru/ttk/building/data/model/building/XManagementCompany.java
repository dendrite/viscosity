package ru.ttk.building.data.model.building;

import ru.ttk.building.data.model.BDocument;

import java.util.HashSet;
import java.util.Set;

/**
 */
public class XManagementCompany extends BDocument {

    private XBuilding building;
    private String name;
    private String address;
    private Set<XManagementCompanyContact> contacts;

    public XManagementCompany(){
        super();
        this.contacts = new HashSet<XManagementCompanyContact>();
        this.name = "";
        this.address = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // TODO need to change it for lazy load - LazyCollectionOption.TRUE
    public Set<XManagementCompanyContact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<XManagementCompanyContact> contacts) {
        this.contacts = contacts;
    }

    public XBuilding getBuilding() {
        return building;
    }

    public void setBuilding(XBuilding building) {
        this.building = building;
    }

}
