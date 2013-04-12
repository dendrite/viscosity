package ru.ttk.building.data.model.building;


import java.io.Serializable;

public class XAddress implements Serializable {

    private String state;
    private String region;
    private String city;
    private String street;
    private String building;
    private String section;
    private String housing;

    public XAddress(){
        state = "";
        region = "";
        city = "";
        street = "";
        building = "";
        section = "";
        housing = "";
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getHousing() {
        return housing;
    }

    public void setHousing(String housing) {
        this.housing = housing;
    }

    @Override
    public String toString(){

        StringBuilder sb = new StringBuilder();

        sb.append("\n state:").append(state);
        sb.append("\n region:").append(region);
        sb.append("\n city:").append(city);
        sb.append("\n street:").append(street);
        sb.append("\n building:").append(building);
        sb.append("\n section:").append(section);
        sb.append("\n housing:").append(housing);

        return sb.toString();
    }

}
