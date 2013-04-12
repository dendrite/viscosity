package ru.ttk.building.data.model.core.dictionary;

import ru.ttk.building.data.model.BDocument;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class XDictionary extends BDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String owningApplication;
    private XDictionary parentDictionary;
    private List<XDictionaryItem> items;

    public XDictionary() {
        super();
        items = new ArrayList<XDictionaryItem>();
        this.owningApplication = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwningApplication() {
        return owningApplication;
    }

    public void setOwningApplication(String owningApplication) {
        this.owningApplication = owningApplication;
    }

    public List<XDictionaryItem> getItems() {
        return items;
    }

    public void setItems(List<XDictionaryItem> items) {
        this.items = items;
    }

    public XDictionary getParentDictionary() {
        return parentDictionary;
    }

    public void setParentDictionary(XDictionary parentDictionary) {
        this.parentDictionary = parentDictionary;
    }

    @Override
    public String getDisplayName(){
        return this.getName();
    }

    public void setId(String id){
        super.setId(id);
    }

    @Override
    public String toString() {
        return " id:" + this.getId() + " name:" + this.getName() + " items:\n" + this.getItems();
    }
}
