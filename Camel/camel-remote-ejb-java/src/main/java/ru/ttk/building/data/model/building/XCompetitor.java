package ru.ttk.building.data.model.building;

import ru.ttk.building.data.model.BDocument;
import ru.ttk.building.data.model.core.XReference;
import ru.ttk.building.data.model.core.dictionary.XDictionaryItem;


/**
 *
 */
public class XCompetitor extends BDocument {

    private XBuilding building;
    private XReference<XDictionaryItem> competitorType;

    public XCompetitor(){
        super();
        this.building = null;
        this.competitorType = new XReference<XDictionaryItem>();
    }

    public XBuilding getBuilding() {
        return building;
    }

    public void setBuilding(XBuilding building) {
        this.building = building;
    }

    public XReference<XDictionaryItem> getCompetitorType() {
        return competitorType;
    }

    public void setCompetitorType(XReference<XDictionaryItem> competitorType) {
        this.competitorType = competitorType;
    }

    @Override
    public boolean equals(Object object){
        XCompetitor xCompetitorLocal = (XCompetitor)object;

        if( xCompetitorLocal.getCompetitorType() != null && this.getCompetitorType() != null){
            if(xCompetitorLocal.getCompetitorType().getObjectId().equalsIgnoreCase(this.getCompetitorType().getObjectId())){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        if(this.getCompetitorType() != null){
            return this.getCompetitorType().getObjectId().hashCode();
        }
        return -1;
    }

}
