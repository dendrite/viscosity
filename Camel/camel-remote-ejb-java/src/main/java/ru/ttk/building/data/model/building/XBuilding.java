package ru.ttk.building.data.model.building;

import ru.ttk.building.data.model.BDocument;
import ru.ttk.building.data.model.core.XReference;
import ru.ttk.building.data.model.core.dictionary.XDictionaryItem;
import ru.ttk.gateway.data.model.GatewayPerson;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 *
 *   //TODO need a @UniqueConstraint - для номера дома и его адреса и т.п.
 *
 *
 */
public class XBuilding extends BDocument {

    private static final long serialVersionUID = 5793817825590192333L;

	private XAddress address;
    private Date lastRevision;
    private String entranceCount;
    private String floors;
    private String flatsPerEntrance;
    private String flatCount;
    private String flatPerFloor;
	private XReference<XDictionaryItem> buildingType;
	private XReference<XDictionaryItem> connectionStatus;
	private GatewayPerson lastRevisionPerson;
	private String comments;
	private Set<XUnit> units;
	private Set<XEntrance> entrances;
    private XManagementCompany managementCompany;
    private Set<XCompetitor> competitors;
    private Set<XInspection> inspections;

    public XBuilding() {
        super();
        this.address= new XAddress();
        this.units= new HashSet<XUnit>();

        this.entrances = new HashSet<XEntrance>();
        this.managementCompany = new XManagementCompany();
        this.lastRevisionPerson = null;

        this.entranceCount      = "";
        this.floors             = "";
        this.flatsPerEntrance   = "";
        this.flatCount          = "";
        this.flatPerFloor       = "";

        this.comments           = "";
        this.lastRevision       = new Date();
        this.competitors        = new HashSet<XCompetitor>();
    }


    public Set<XInspection> getInspections() {
        return inspections;
    }

    public void setInspections(Set<XInspection> inspections) {
        this.inspections = inspections;
    }

    /**
     * Fake for JAON Jackson mapper
     * @param units
     */
    public XBuilding(String units){
        super();
        this.address= new XAddress();
        this.units= new HashSet<XUnit>();

        this.entrances = new HashSet<XEntrance>();
        this.managementCompany = new XManagementCompany();
        this.lastRevisionPerson = new GatewayPerson();

        this.entranceCount      = "";
        this.floors             = "";
        this.flatsPerEntrance   = "";
        this.flatCount          = "";
        this.flatPerFloor       = "";

        this.comments           = "";
        this.lastRevision       = new Date();
        this.competitors        = new HashSet<XCompetitor>();
    }

    public XBuilding(Set<XUnit> units){
        super();
        this.address= new XAddress();
        this.units = units;

        this.entrances = new HashSet<XEntrance>();
        this.managementCompany = new XManagementCompany();
        this.lastRevisionPerson = new GatewayPerson();

        this.entranceCount      = "";
        this.floors             = "";
        this.flatsPerEntrance   = "";
        this.flatCount          = "";
        this.flatPerFloor       = "";

        this.comments           = "";
        this.lastRevision       = new Date();
        this.competitors        = new HashSet<XCompetitor>();
    }


    public Set<XCompetitor> getCompetitors() {
        return competitors;
    }

    public void setCompetitors(Set<XCompetitor> competitors) {
        this.competitors = competitors;
    }

    public XAddress getAddress() {
		return address;
	}

	public void setAddress(XAddress address) {
		this.address = address;
	}


	public XReference<XDictionaryItem> getBuildingType() {
		return buildingType;
	}

	public void setBuildingType(XReference<XDictionaryItem> buildingType) {
		this.buildingType = buildingType;
	}


	public XReference<XDictionaryItem> getConnectionStatus() {
		return connectionStatus;
	}

	public void setConnectionStatus(XReference<XDictionaryItem> connectionStatus) {
		this.connectionStatus = connectionStatus;
	}

	public Date getLastRevision() {
		return lastRevision;
	}

	public void setLastRevision(Date lastRevision) {
		this.lastRevision = lastRevision;
	}

	public GatewayPerson getLastRevisionPerson() {
		return lastRevisionPerson;
	}

	public void setLastRevisionPerson(GatewayPerson lastRevisionPerson) {
		this.lastRevisionPerson = lastRevisionPerson;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}


	public Set<XUnit> getUnits() {
		return units;
	}

	public void setUnits(Set<XUnit> units) {
		this.units = units;
	}


	public Set<XEntrance> getEntrances() {
		return entrances;
	}

	public void setEntrances(Set<XEntrance> entrances) {
		this.entrances = entrances;
	}

    @Override
    public String toString(){
        return " id:" + this.getId() + " type:" + this.buildingType + " status:" + this.connectionStatus;
    }

    @Override
    public String getDisplayName() {
        return this.getAddress().getCity() + " " + this.getAddress().getStreet() + " " + this.getAddress().getBuilding();
    }

    public String getEntranceCount() {
        return entranceCount;
    }

    public void setEntranceCount(String entranceCount) {
        this.entranceCount = entranceCount;
    }

    public String getFloors() {
        return floors;
    }

    public void setFloors(String floors) {
        this.floors = floors;
    }

    public String getFlatsPerEntrance() {
        return flatsPerEntrance;
    }

    public void setFlatsPerEntrance(String flatsPerEntrance) {
        this.flatsPerEntrance = flatsPerEntrance;
    }

    public String getFlatCount() {
        return flatCount;
    }

    public void setFlatCount(String flatCount) {
        this.flatCount = flatCount;
    }

    public String getFlatPerFloor() {
        return flatPerFloor;
    }

    public void setFlatPerFloor(String flatPerFloor) {
        this.flatPerFloor = flatPerFloor;
    }

    public XManagementCompany getManagementCompany() {
        return managementCompany;
    }

    public void setManagementCompany(XManagementCompany managementCompany) {
        this.managementCompany = managementCompany;
    }

}
