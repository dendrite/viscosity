package com.test.jsfbootstrap.controller;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 */
@Named("typeViewer")
@ViewScoped
public class TypeViewer {

    private List<HouseType> list;

    private String nnn = "ggg";
    private boolean lvl = true;

    private String inpText;


    private String selectedLevel3;

    public String getSelectedLevel3() {
        return selectedLevel3;
    }

    public void setSelectedLevel3(String selectedLevel3) {
        this.selectedLevel3 = selectedLevel3;
    }

    public String getInpText() {
        return inpText;
    }

    public void setInpText(String inpText) {
        this.inpText = inpText;
    }

    public boolean isLvl() {
        return lvl;
    }

    public void setLvl(boolean lvl) {
        this.lvl = lvl;
    }

    public String getNnn() {
        return nnn;
    }

    public void setter(String v){
        vv = v;
    }

    public void setNnn(String nnn) {
        this.nnn = nnn;
    }

    private String vv;

    public String getVv() {
        return vv;
    }

    public void setVv(String vv) {
        this.vv = vv;
    }

    //action listener event
    public void attrListener(AjaxBehaviorEvent event){
        System.out.println("CLICKED");
        System.out.println(event.getComponent().getAttributes());
        Map<String, Object> mp =  event.getComponent().getAttributes();
        Set<String> set = mp.keySet();
        for(String str:set){
            System.out.println("" + str + ":" + mp.get(str));
        }

        System.out.println("VV:" + vv);
        System.out.println((String)event.getComponent().getAttributes().get("username"));
       lvl = false;
        selectedLevel3 = "fff";

    }

    public List<HouseType> getList() {

        String[] str = {"деревня", "город", "село", "будка", "что-то",
                "как-то", "площадь", "переулок", "станция", "хутор"};

        String[] css = {"label-success", "label-important", "label-important", "label-info", "label-info",
                "label-info", "label-info", "label-success", "label-warning", "label-warning"};

        List<HouseType> list = new ArrayList<HouseType>();
        for(int i=0; i<10; i++){
            HouseType houseType = new HouseType();
            houseType.setName(str[i]);
            houseType.setId("ff1" + i);
            houseType.setCssClass(css[i]);
            list.add(houseType);
        }

        return list;
    }

    public void setList(List<HouseType> list) {
        this.list = list;
    }
}
