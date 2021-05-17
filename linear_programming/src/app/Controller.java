package app;

import classes.Problem;
import classes.Stock;
import classes.UnitOutlay;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class Controller {

    public TableColumn<Stock, String> col_stockName;
    public TableColumn<UnitOutlay, String> col_uoName;
    public TableColumn<UnitOutlay, Double> col_uoVal;
    public TableView<Stock> tableViewStock;
    public TableView<UnitOutlay> tableViewUnitOutlay;
    public TableView<Problem> tableProblem;
    public TableColumn<Problem, String> col_problemName;
    public TableColumn<Problem, Integer> col_problemAmount;
    public TableColumn<Problem, Double> col_problemPrice;
    ObservableList<Problem> problemList = FXCollections.observableArrayList();
    ObservableList<Stock> stockList = FXCollections.observableArrayList();
    ObservableList<UnitOutlay> uoList = FXCollections.observableArrayList();

    int problemSize = -1;

    public void problemAddButton() {
        Problem problem = new Problem();
        problemList.add(problem);
    }

    public void problemDeleteButton() {
        int index = tableProblem.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            System.out.println("Select problem");
            return;
        }
        problemList.remove(index);
    }

    public void stockAddButton() {
        Stock stock = new Stock();
        ObservableList<UnitOutlay> temp = stock.getUnitOutlayList();
        for (Problem p : problemList) {
            temp.add(new UnitOutlay(p.getName(), -1));
        }
        stockList.add(stock);
    }

    public void stockDeleteButton() {
        int index = tableViewStock.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            System.out.println("Select stock");
            return;
        }
        stockList.remove(index);
    }

    public void showUO() {
        Stock stock = tableViewStock.getSelectionModel().getSelectedItem();
        if (stock == null) {
            System.out.println("Select stock");
            return;
        }
        uoList = stock.getUnitOutlayList();
        tableViewUnitOutlay.setItems(uoList);
    }

//    public void uoAddButton() {
//        Stock stock = tableViewStock.getSelectionModel().getSelectedItem();
//        UnitOutlay unitOutlay = new UnitOutlay("...",-1);
//        stock.getUnitOutlayList().add(unitOutlay);
//    }
//
//    public void uoDeleteButton() {
//        Stock stock = tableViewStock.getSelectionModel().getSelectedItem();
//        int index = tableViewUnitOutlay.getSelectionModel().getSelectedIndex();
//        stock.getUnitOutlayList().remove(index);
//    }

    public void onStockNameEdit(TableColumn.CellEditEvent<Stock, String> stockStringCellEditEvent) {
        Stock stock = tableViewStock.getSelectionModel().getSelectedItem();
        stock.setName(stockStringCellEditEvent.getNewValue());
    }

    public void onUONameEdit(TableColumn.CellEditEvent<UnitOutlay, String> stockStringCellEditEvent) {
        UnitOutlay unitOutlay = tableViewUnitOutlay.getSelectionModel().getSelectedItem();
        unitOutlay.setName(stockStringCellEditEvent.getNewValue());
    }

    public void onUOValueEdit(TableColumn.CellEditEvent<UnitOutlay, Double> stockStringCellEditEvent) {
        UnitOutlay unitOutlay = tableViewUnitOutlay.getSelectionModel().getSelectedItem();
        unitOutlay.setValue(stockStringCellEditEvent.getNewValue());
    }

    public void onProblemNameEdit(TableColumn.CellEditEvent<Problem, String> stockStringCellEditEvent) {
        Problem problem = tableProblem.getSelectionModel().getSelectedItem();
        problem.setName(stockStringCellEditEvent.getNewValue());
    }

    public void onProblemAmountEdit(TableColumn.CellEditEvent<Problem, Integer> stockStringCellEditEvent) {
        Problem problem = tableProblem.getSelectionModel().getSelectedItem();
        problem.setAmount(stockStringCellEditEvent.getNewValue());
    }

    public void onProblemPriceEdit(TableColumn.CellEditEvent<Problem, Double> stockStringCellEditEvent) {
        Problem problem = tableProblem.getSelectionModel().getSelectedItem();
        problem.setPrice(stockStringCellEditEvent.getNewValue());
    }

    @FXML
    public void initialize() {
        col_stockName.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_uoName.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_uoVal.setCellValueFactory(new PropertyValueFactory<>("value"));
        col_problemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_problemAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        col_problemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        col_stockName.setCellFactory(TextFieldTableCell.forTableColumn());
        col_uoName.setCellFactory(TextFieldTableCell.forTableColumn());
        col_uoVal.setCellFactory(TextFieldTableCell.<UnitOutlay, Double>forTableColumn(new DoubleStringConverter()));
        col_problemName.setCellFactory(TextFieldTableCell.forTableColumn());
        col_problemAmount.setCellFactory(TextFieldTableCell.<Problem, Integer>forTableColumn(new IntegerStringConverter()));
        col_problemPrice.setCellFactory(TextFieldTableCell.<Problem, Double>forTableColumn(new DoubleStringConverter()));

        tableViewStock.setItems(stockList);
        tableProblem.setItems(problemList);
    }

}