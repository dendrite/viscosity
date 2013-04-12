package ru.ttk.building.data.model.building;

import ru.ttk.building.data.model.BDocument;
import ru.ttk.building.model.type.InspectionType;
import ru.ttk.gateway.data.model.GatewayPerson;

import java.util.Date;

/**
 *
 */
public class XInspectionResult extends BDocument {

    private XInspection inspection;
    private XUnit unit;
    private Date date;
    private String comments;
    private GatewayPerson modifierPerson;
    private InspectionType inspectionType;

    public XInspectionResult(){
        super();
        this.comments = "";
        this.date = new Date();
        this.inspection = null;
        this.modifierPerson = null;
        this.inspectionType = InspectionType.INSPECTION_ABSENT;
    }

    public XInspection getInspection() {
        return inspection;
    }

    public void setInspection(XInspection inspection) {
        this.inspection = inspection;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public GatewayPerson getModifierPerson() {
        return modifierPerson;
    }

    public void setModifierPerson(GatewayPerson modifierPerson) {
        this.modifierPerson = modifierPerson;
    }

    public XUnit getUnit() {
        return unit;
    }

    public void setUnit(XUnit unit) {
        this.unit = unit;
    }

    public InspectionType getInspectionType() {
        return inspectionType;
    }

    public void setInspectionType(InspectionType inspectionType) {
        this.inspectionType = inspectionType;
    }

    @Override
    public String toString(){
        return "id:" + this.getId() + " date:" + this.date + " person:" + this.modifierPerson.getDisplayName() + " inspectionType:" +  this.inspectionType.toString();
    }

}
