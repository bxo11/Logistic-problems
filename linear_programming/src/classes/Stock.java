package classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Stock {

    String name;
    static int iter = 1;
    int maxProduction;
    ObservableList<UnitOutlay> unitOutlayList;

    public Stock() {
        this.name = "S" + iter;
        this.unitOutlayList = FXCollections.observableArrayList();
        this.maxProduction = -1;

        iter++;
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
