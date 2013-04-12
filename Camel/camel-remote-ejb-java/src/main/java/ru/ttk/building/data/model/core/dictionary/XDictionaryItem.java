package ru.ttk.building.data.model.core.dictionary;

import ru.ttk.building.data.model.BDocument;

import java.io.Serializable;


public class XDictionaryItem extends BDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;


    private XDictionary dictionary;

    public XDictionaryItem(String id){
        super();
        this.setId(id);
        this.dictionary = null;
    }

    public XDictionaryItem(Object object){
        super();
        this.dictionary = null;
    }

    public XDictionaryItem(){
        super();
        this.dictionary = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public XDictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(XDictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public String toString() {
        return " id:" + this.getId() + " name:" + this.name + "\n";
    }

    @Override
    public String getDisplayName(){
        return this.name;
    }

    public void setId(String id){
        super.setId(id);
    }

}
