package app;
import classes.Restriction;
import classes.Problem;
import classes.Stock;
import classes.UnitOutlay;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class Controller {

    public TableColumn<Stock, String> col_stockName;
    public TableColumn<Stock, Integer> col_stockMaxProduction;
    public TableColumn<UnitOutlay, String> col_uoName;
    public TableColumn<UnitOutlay, Double> col_uoVal;
    public TableColumn<Problem, String> col_problemName;
    public TableColumn<Problem, Double> col_problemPrice;
    public TableColumn<Restriction, String> col_restrictionVariable;
    public TableColumn<Restriction, String> col_restrictionSign;
    public TableColumn<Restriction, Double> col_restrictionLowLim;
    public TableColumn<Restriction, Double> col_restrictionUpLim;
    public TableView<Stock> tableViewStock;
    public TableView<UnitOutlay> tableViewUnitOutlay;
    public TableView<Problem> tableViewProblem;
    public TableView<Restriction> tableViewRestriction;
    ObservableList<Problem> problemList = FXCollections.observableArrayList();
    ObservableList<Stock> stockList = FXCollections.observableArrayList();
    ObservableList<UnitOutlay> uoList = FXCollections.observableArrayList();
    ObservableList<Restriction> restrictionList = FXCollections.observableArrayList();

    public void problemAddButton() {
        Problem problem = new Problem();
        problemList.add(problem);
    }

    public void problemDeleteButton() {
        int index = tableViewProblem.getSelectionModel().getSelectedIndex();
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

    public void restrictionAddButton() {
        Problem problem = tableViewProblem.getSelectionModel().getSelectedItem();
        if (problem == null) {
            System.out.println("Select problem");
            return;
        }
        Restriction restriction = new Restriction(problem.getName());
        restrictionList.add(restriction);
    }

    public void restrictionDeleteButton() {
        int index = tableViewRestriction.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            System.out.println("Select problem");
            return;
        }
        restrictionList.remove(index);
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

    public void onStockNameEdit(TableColumn.CellEditEvent<Stock, String> event) {
        Stock stock = tableViewStock.getSelectionModel().getSelectedItem();
        stock.setName(event.getNewValue());
    }

    public void onStockMaxProductionEdit(TableColumn.CellEditEvent<Stock, Integer> event) {
        Stock stock = tableViewStock.getSelectionModel().getSelectedItem();
        stock.setMaxProduction(event.getNewValue());
    }

    public void onUONameEdit(TableColumn.CellEditEvent<UnitOutlay, String> event) {
        UnitOutlay unitOutlay = tableViewUnitOutlay.getSelectionModel().getSelectedItem();
        unitOutlay.setName(event.getNewValue());
    }

    public void onUOValueEdit(TableColumn.CellEditEvent<UnitOutlay, Double> event) {
        UnitOutlay unitOutlay = tableViewUnitOutlay.getSelectionModel().getSelectedItem();
        unitOutlay.setValue(event.getNewValue());
    }

    public void onProblemNameEdit(TableColumn.CellEditEvent<Problem, String> event) {
        Problem problem = tableViewProblem.getSelectionModel().getSelectedItem();
        problem.setName(event.getNewValue());
    }

    public void onProblemPriceEdit(TableColumn.CellEditEvent<Problem, Double> event) {
        Problem problem = tableViewProblem.getSelectionModel().getSelectedItem();
        problem.setPrice(event.getNewValue());
    }

    public void onRestrictionLowerLimitEdit(TableColumn.CellEditEvent<Restriction, Double> event) {
        Restriction restriction = tableViewRestriction.getSelectionModel().getSelectedItem();
        restriction.setLowerLimit(event.getNewValue());
    }

    public void onRestrictionUpperLimitEdit(TableColumn.CellEditEvent<Restriction, Double> event) {
        Restriction restriction = tableViewRestriction.getSelectionModel().getSelectedItem();
        restriction.setUpperLimit(event.getNewValue());
    }

    @FXML
    public void initialize() {
        col_stockName.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_stockMaxProduction.setCellValueFactory(new PropertyValueFactory<>("maxProduction"));
        col_uoName.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_uoVal.setCellValueFactory(new PropertyValueFactory<>("value"));
        col_problemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_problemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_restrictionVariable.setCellValueFactory(new PropertyValueFactory<>("variable"));
        col_restrictionSign.setCellValueFactory(new PropertyValueFactory<>("sign"));
        col_restrictionLowLim.setCellValueFactory(new PropertyValueFactory<>("lowerLimit"));
        col_restrictionUpLim.setCellValueFactory(new PropertyValueFactory<>("upperLimit"));

        col_stockName.setCellFactory(TextFieldTableCell.forTableColumn());
        col_stockMaxProduction.setCellFactory(TextFieldTableCell.<Stock, Integer>forTableColumn(new IntegerStringConverter()));
        col_uoName.setCellFactory(TextFieldTableCell.forTableColumn());
        col_uoVal.setCellFactory(TextFieldTableCell.<UnitOutlay, Double>forTableColumn(new DoubleStringConverter()));
        col_problemName.setCellFactory(TextFieldTableCell.forTableColumn());
        col_problemPrice.setCellFactory(TextFieldTableCell.<Problem, Double>forTableColumn(new DoubleStringConverter()));
        col_restrictionLowLim.setCellFactory(TextFieldTableCell.<Restriction, Double>forTableColumn(new DoubleStringConverter()));
        col_restrictionUpLim.setCellFactory(TextFieldTableCell.<Restriction, Double>forTableColumn(new DoubleStringConverter()));

        tableViewStock.setItems(stockList);
        tableViewProblem.setItems(problemList);
        tableViewRestriction.setItems(restrictionList);
    }


}