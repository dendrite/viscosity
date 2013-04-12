package ru.ttk.gateway.data.model;

import ru.ttk.building.data.model.building.XInspection;
import ru.ttk.building.data.model.building.XInspectionResult;
import ru.ttk.gateway.service.IGatewayEntity;
import ru.ttk.gateway.utils.GatewayEntityUtils;

import java.io.Serializable;


/**
 *
 */
public class GatewayPerson extends GatewayEntity implements IGatewayEntity, Serializable {

    private XInspectionResult inspectionResult;
    private XInspection inspection;

    public GatewayPerson() {
        this.fullClassName = GatewayEntityUtils.PPERSON_FULL_CLASS_NAME;
        this.dataSourceName = GatewayEntityUtils.DATA_SOURCE_ORGANISATION_STRUCTURE;
    }

    public GatewayPerson(String id, String fullClassName, String displayName, String dataSourceName){
        super(id,fullClassName,displayName, GatewayEntityUtils.DATA_SOURCE_ORGANISATION_STRUCTURE);
    }

    public XInspection getInspection() {
        return inspection;
    }

    public void setInspection(XInspection inspection) {
        this.inspection = inspection;
    }

    public XInspectionResult getInspectionResult() {
        return inspectionResult;
    }

    public void setInspectionResult(XInspectionResult inspectionResult) {
        this.inspectionResult = inspectionResult;
    }

    /**
     * Нужно было переопределить - т.к. JSF не отрабатывал корректно SelectOneMenu
     *
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object){
        if(object == this){
            return true;
        }

        if(object == null || !(object instanceof GatewayPerson)){
            return false;
        }

        GatewayPerson localPerson = (GatewayPerson)object;
        if(localPerson.getId() != null && this.getId() != null){
            return localPerson.getId().equalsIgnoreCase(this.getId());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }

}
