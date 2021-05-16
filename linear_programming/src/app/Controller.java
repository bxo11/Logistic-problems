package app;

import classes.Stock;
import classes.UnitOutlay;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    public TableColumn<Stock, String> col_stockName;
    public TableColumn<UnitOutlay, String> col_uoName;
    public TableColumn<UnitOutlay, Double> col_uoVal;
    public TableView<Stock> tableViewStock;
    public TableView<UnitOutlay> tableViewUnitOutlay;
    public Button buttonStockAdd;
    public Button buttonStockDel;
    public Button buttonUOAdd;
    public Button buttonUODel;
    ObservableList<Stock> stockList = FXCollections.observableArrayList();
    ObservableList<UnitOutlay> uoList = FXCollections.observableArrayList();

    public void stockAddButton() {
        Stock stock = new Stock("...");
        stockList.add(stock);
    }

    public void stockDeleteButton() {
        int index = tableViewStock.getSelectionModel().getSelectedIndex();
        stockList.remove(index);
    }

    public void showUO() {
        Stock stock = tableViewStock.getSelectionModel().getSelectedItem();
        uoList = stock.getUnitOutlayList();
        tableViewUnitOutlay.setItems(uoList);
    }


    public void uoAddButton() {
        Stock stock = tableViewStock.getSelectionModel().getSelectedItem();
        UnitOutlay unitOutlay = new UnitOutlay("...",-1);
        stock.getUnitOutlayList().add(unitOutlay);
    }

    public void uoDeleteButton() {
        Stock stock = tableViewStock.getSelectionModel().getSelectedItem();
        int index = tableViewUnitOutlay.getSelectionModel().getSelectedIndex();
        stock.getUnitOutlayList().remove(index);
    }

    public void onStockNameEdit(TableColumn.CellEditEvent<Stock, String> stockStringCellEditEvent) {
        Stock stock = tableViewStock.getSelectionModel().getSelectedItem();
        stock.setName((String) stockStringCellEditEvent.getNewValue());
    }

    public void onUONameEdit(TableColumn.CellEditEvent<Stock, String> stockStringCellEditEvent) {
        UnitOutlay unitOutlay = tableViewUnitOutlay.getSelectionModel().getSelectedItem();
        unitOutlay.setName((String) stockStringCellEditEvent.getNewValue());
    }

    public void onUOValueEdit(TableColumn.CellEditEvent<Stock, Double> stockStringCellEditEvent) {
        UnitOutlay unitOutlay = tableViewUnitOutlay.getSelectionModel().getSelectedItem();
        unitOutlay.setValue(stockStringCellEditEvent.getNewValue());
    }

    @FXML
    public void initialize() {
        Stock tasma = new Stock("tasma");
        ObservableList<UnitOutlay> tl = FXCollections.observableArrayList();
        tl.add(new UnitOutlay("W1",1));
        tl.add(new UnitOutlay("W2",2));
        tasma.setUnitOutlayList(tl);

        Stock rura =  new Stock("rura");
        ObservableList<UnitOutlay> tl2 = FXCollections.observableArrayList();
        tl2.add(new UnitOutlay("W1",3));
        tl2.add(new UnitOutlay("W2",4));
        rura.setUnitOutlayList(tl2);

        stockList.add(tasma);
        stockList.add(rura);

        col_stockName.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_uoName.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_uoVal.setCellValueFactory(new PropertyValueFactory<>("value"));
        tableViewStock.setItems(stockList);

        col_stockName.setCellFactory(TextFieldTableCell.forTableColumn());
        col_uoName.setCellFactory(TextFieldTableCell.forTableColumn());
        col_uoVal.setCellFactory(TextFieldTableCell.<UnitOutlay, Double>forTableColumn(new DoubleStringConverter()));

    }


}