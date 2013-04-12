package ru.ttk.building.data.model.building;

import ru.ttk.building.data.model.BDocument;
import ru.ttk.building.data.model.core.XReference;
import ru.ttk.building.data.model.core.dictionary.XDictionaryItem;


/**
 *
 */
public class XAdvertisement extends BDocument {

    private XEntrance entrance;
    private XReference<XDictionaryItem> advertisementType;

    public XAdvertisement() {
        super();
        this.advertisementType = new XReference<XDictionaryItem>();
        this.entrance = null;
    }

    public XReference<XDictionaryItem> getAdvertisementType() {
        return advertisementType;
    }

    public void setAdvertisementType(XReference<XDictionaryItem> advertisementType) {
        this.advertisementType = advertisementType;
    }

    public XEntrance getEntrance() {
        return entrance;
    }

    public void setEntrance(XEntrance entrance) {
        this.entrance = entrance;
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof XAdvertisement) {
            XAdvertisement xAdvertisementLocal = (XAdvertisement) object;

            if (xAdvertisementLocal.getAdvertisementType() != null && this.getAdvertisementType() != null) {
                if (xAdvertisementLocal.getAdvertisementType().getObjectId().equalsIgnoreCase(this.getAdvertisementType().getObjectId())) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        if(this.getAdvertisementType() != null){
            return this.getAdvertisementType().getObjectId().hashCode();
        }
        return -1;
    }

}
