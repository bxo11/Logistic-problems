package classes;

import java.util.List;

public class Stock {

    String name;
    List<UnitOutlay> unitOutlayList;

    public Stock(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UnitOutlay> getUnitOutlayList() {
        return unitOutlayList;
    }

    public void setUnitOutlayList(List<UnitOutlay> unitOutlayList) {
        this.unitOutlayList = unitOutlayList;
    }
}
