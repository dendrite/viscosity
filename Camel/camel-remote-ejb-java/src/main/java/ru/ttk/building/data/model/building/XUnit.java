package ru.ttk.building.data.model.building;

import ru.ttk.building.data.model.BDocument;
import ru.ttk.building.data.model.core.XReference;
import ru.ttk.building.data.model.core.dictionary.XDictionaryItem;
import ru.ttk.building.model.type.UnitType;

import java.util.HashSet;
import java.util.Set;

public class XUnit extends BDocument {

    private static final long serialVersionUID = -8404119885820465546L;

    private XBuilding building;
    private XEntrance entrance;

    private XReference<XDictionaryItem> connectionStatus;
    private String comments;
    private String unitNumber;
    private Set<XSubscriber> subscribers;
    private UnitType unitType;

    private Set<XInspectionResult> inspectionResult;

    public XUnit() {
        super();
        this.subscribers = new HashSet<XSubscriber>();
        this.comments = "";
        this.unitNumber = "";
        this.unitType = UnitType.FLAT;
    }

    /**
     * Just fake for Jackson
     * @param string
     */
    public XUnit(String string){
        super();
        subscribers = new HashSet<XSubscriber>();
        this.comments = "";
        this.unitNumber = "";
        this.unitType = UnitType.FLAT;
    }

    public XBuilding getBuilding() {
        return building;
    }

    public void setBuilding(XBuilding building) {
        this.building = building;
    }

    public XEntrance getEntrance() {
        return entrance;
    }

    public void setEntrance(XEntrance entrance) {
        this.entrance = entrance;
    }

    public XReference<XDictionaryItem> getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(XReference<XDictionaryItem> connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public Set<XSubscriber> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<XSubscriber> subscribers) {
        this.subscribers = subscribers;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public Set<XInspectionResult> getInspectionResult() {
        return inspectionResult;
    }

    public void setInspectionResult(Set<XInspectionResult> inspectionResult) {
        this.inspectionResult = inspectionResult;
    }

    @Override
    public String toString(){
        return " №:" + unitNumber + " comments:" + comments + " type:" + this.unitType + " id:" + this.getId();
    }

}
