package ru.ttk.test.interfaces;

import ru.ttk.building.data.model.building.XBuilding;

import javax.ejb.Remote;
import java.io.Serializable;

/**
 *
 */
@Remote
public interface ISimpleRemoteEJB extends Serializable{
    public String getNewValue(String value);
    public String getNewOld();

    public XBuilding getPureBuildingList(String buildingId);
    public byte[] getKryoBuildingList(String buildingId);

}
