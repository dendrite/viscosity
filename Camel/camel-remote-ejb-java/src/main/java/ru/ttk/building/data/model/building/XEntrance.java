package ru.ttk.building.data.model.building;

import ru.ttk.building.data.model.BDocument;
import ru.ttk.building.data.model.core.XReference;
import ru.ttk.building.data.model.core.dictionary.XDictionaryItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class XEntrance extends BDocument {

    private static final long serialVersionUID = 7749469463478301152L;

    private XBuilding building;
    private XReference<XDictionaryItem> connectionStatus;
    private String comments;
    private String entranceNumber;
    private String passCode;
    private List<XUnit> units;
    private Set<XAdvertisement> advertisement;

    public XEntrance() {
        super();
        units = new ArrayList<XUnit>();
        this.advertisement = new HashSet<XAdvertisement>();
        this.passCode = "";
        this.entranceNumber = "";
        this.comments = "";
    }

    /**
     * Just fake for JSON Jackson mapper
     * @param string
     */
    public XEntrance(String string){

    }

    public Set<XAdvertisement> getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Set<XAdvertisement> advertisement) {
        this.advertisement = advertisement;
    }

    public XBuilding getBuilding() {
        return building;
    }

    public void setBuilding(XBuilding building) {
        this.building = building;
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

    public String getEntranceNumber() {
        return entranceNumber;
    }

    public void setEntranceNumber(String entracnceNumber) {
        this.entranceNumber = entracnceNumber;
    }

    public String getPassCode() {
        return passCode;
    }

    public void setPassCode(String passCode) {
        this.passCode = passCode;
    }

    public List<XUnit> getUnits() {
        return units;
    }

    public void setUnits(List<XUnit> units) {
        this.units = units;
    }

    public String toString() {
        return "Подъезд: " + String.valueOf(entranceNumber);
    }

    public String assignDisplayName(){
        return "Подъезд: " + this.entranceNumber;
    }

}
