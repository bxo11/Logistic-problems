package classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Stock {

    String name;
    int maxProduction;
    ObservableList<UnitOutlay> unitOutlayList;

    public Stock() {
        this.name = "...";
        this.unitOutlayList = FXCollections.observableArrayList();
        this.maxProduction = -1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObservableList<UnitOutlay> getUnitOutlayList() {
        return unitOutlayList;
    }

    public void setUnitOutlayList(ObservableList<UnitOutlay> unitOutlayList) {
        this.unitOutlayList = unitOutlayList;
    }

    public int getMaxProduction() {
        return maxProduction;
    }

    public void setMaxProduction(int maxProduction) {
        this.maxProduction = maxProduction;
    }
}
