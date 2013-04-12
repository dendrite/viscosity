package ru.ttk.building.data.model.building;

import ru.ttk.building.data.model.BDocument;
import ru.ttk.building.model.type.InspectionType;

import java.util.Date;

/**
 *
 */
public class XInspectionStatisticAggregator extends BDocument {

    private XInspection inspection;
    private Date date;
    private InspectionType inspectionType;
    private Long count;
    private Long incarnation;

    public XInspectionStatisticAggregator(){
        super();
        this.inspection = null;
        this.date = new Date();
        this.inspectionType = InspectionType.INSPECTION_ABSENT;
        this.count = -1L;
        this.incarnation = -1L;
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

    public InspectionType getInspectionType() {
        return inspectionType;
    }

    public void setInspectionType(InspectionType inspectionType) {
        this.inspectionType = inspectionType;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getIncarnation() {
        return incarnation;
    }

    public void setIncarnation(Long incarnation) {
        this.incarnation = incarnation;
    }

    @Override
    public String toString(){
        return " id:" + this.getId() + " type:" + this.inspectionType.toString() + " count:" + this.count + " incarnation:" + this.incarnation + " inspection:" + this.getInspection().getId();
    }

}
