package classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Stock {

    String name;
    ObservableList<UnitOutlay> unitOutlayList = FXCollections.observableArrayList();

    public Stock(String name) {
        this.name = name;
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
}
