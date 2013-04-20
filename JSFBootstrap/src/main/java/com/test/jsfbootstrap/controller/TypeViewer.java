package com.test.jsfbootstrap.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Named("typeViewer")
@RequestScoped
public class TypeViewer {

    private List<HouseType> list;

    public List<HouseType> getList() {

        String[] str = {"деревня", "город", "село", "будка", "что-то",
                "как-то", "площадь", "переулок", "станция", "хутор"};

        String[] css = {"label-success", "label-important", "label-important", "label-info", "label-info",
                "label-info", "label-info", "label-success", "label-warning", "label-warning"};

        List<HouseType> list = new ArrayList<HouseType>();
        for(int i=0; i<10; i++){
            HouseType houseType = new HouseType();
            houseType.setName(str[i]);
            houseType.setId("1" + i);
            houseType.setCssClass(css[i]);
            list.add(houseType);
        }

        return list;
    }

    public void setList(List<HouseType> list) {
        this.list = list;
    }
}
