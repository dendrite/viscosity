package ru.ttk.building.data.model.building;

import ru.ttk.building.data.model.BDocument;
import ru.ttk.building.model.type.InspectionStatus;
import ru.ttk.gateway.data.model.GatewayPerson;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class XInspection extends BDocument {

    private XBuilding building;
    private String comments;
    private Date createDate;
    private Date assignDate;
    private GatewayPerson creatorPerson;
    private GatewayPerson executorPerson;
    private Set<XInspectionResult> inspectionResult;
    private Set<XInspectionStatisticAggregator> inspectionAggregator;
    private InspectionStatus inspectionStatus;

    public XInspection(){
        super();
        this.building = null;
        this.comments = "";
        this.createDate = new Date();
        this.assignDate = new Date();
        this.creatorPerson = null;
        this.executorPerson = null;
        this.inspectionResult = new HashSet<XInspectionResult>();
        this.inspectionAggregator = new HashSet<XInspectionStatisticAggregator>();
        this.inspectionStatus = InspectionStatus.PREPARING;
    }

    public XBuilding getBuilding() {
        return building;
    }

    public void setBuilding(XBuilding building) {
        this.building = building;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(Date assignDate) {
        this.assignDate = assignDate;
    }

    public GatewayPerson getCreatorPerson() {
        return creatorPerson;
    }

    public void setCreatorPerson(GatewayPerson creatorPerson) {
        this.creatorPerson = creatorPerson;
    }

    public GatewayPerson getExecutorPerson() {
        return executorPerson;
    }

    public void setExecutorPerson(GatewayPerson executorPerson) {
        this.executorPerson = executorPerson;
    }

    public Set<XInspectionResult> getInspectionResult() {
        return inspectionResult;
    }

    public void setInspectionResult(Set<XInspectionResult> inspectionResult) {
        this.inspectionResult = inspectionResult;
    }

    public Set<XInspectionStatisticAggregator> getInspectionAggregator() {
        return inspectionAggregator;
    }

    public void setInspectionAggregator(Set<XInspectionStatisticAggregator> inspectionAggregator) {
        this.inspectionAggregator = inspectionAggregator;
    }

    public InspectionStatus getInspectionStatus() {
        return inspectionStatus;
    }

    public void setInspectionStatus(InspectionStatus inspectionStatus) {
        this.inspectionStatus = inspectionStatus;
    }

    @Override
    public String toString(){
        String creator = null;
        String executor = null;
        if(this.creatorPerson != null && this.creatorPerson.getDisplayName() != null){
            creator = this.creatorPerson.getDisplayName();
        }

        if(this.executorPerson != null && this.executorPerson.getDisplayName() != null){
            executor = this.executorPerson.getDisplayName();
        }
        return "id:" + this.getId() + " creator:" + creator + " executor:" + executor + " createDate:" + this.createDate + " assignDate:" + this.assignDate + " status:" + this.inspectionStatus.toString();
    }

}
