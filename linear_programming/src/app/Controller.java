package app;

import classes.Problem;
import classes.Restriction;
import classes.Stock;
import classes.UnitOutlay;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import it.ssc.log.SscLogger;
import it.ssc.pl.milp.LP;
import it.ssc.pl.milp.Solution;
import it.ssc.pl.milp.SolutionType;
import it.ssc.pl.milp.Variable;

import java.util.ArrayList;

public class Controller {

    public TableColumn<Problem, Integer> col_problemMaxAmount;
    public TableColumn<Stock, String> col_stockName;
    public TableColumn<Stock, Integer> col_stockMaxProduction;
    public TableColumn<UnitOutlay, String> col_uoName;
    public TableColumn<UnitOutlay, Double> col_uoVal;
    public TableColumn<Problem, String> col_problemName;
    public TableColumn<Problem, Double> col_problemPrice;
    public TableColumn<Restriction, String> col_restrictionVariable;
    public TableColumn<Restriction, String> col_restrictionSign;
    public TableColumn<Restriction, Double> col_restrictionLimit;
    public TableView<Stock> tableViewStock;
    public TableView<UnitOutlay> tableViewUnitOutlay;
    public TableView<Problem> tableViewProblem;
    public TableView<Restriction> tableViewRestriction;
    public Text income;
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
            temp.add(new UnitOutlay(p.getName()));
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

    public void buttonSolve() throws Exception {
        ArrayList< String > constraints = new ArrayList< String >();

        //goal function
        String goalFunction = "max: ";
        for(Problem p : problemList){
            double c = p.getAmount() * +p.getPrice();
            goalFunction+= c+p.getName()+" + ";
        }
        goalFunction = goalFunction.substring(0, goalFunction.length() - 3);
        System.out.println(goalFunction);
        constraints.add(goalFunction);

        //material constains
        for(Restriction r : restrictionList){
            String res = "";
            res+= r.getVariable() +r.getComboBoxSign().getSelectionModel().getSelectedItem()+r.getLimit();
            System.out.println(res);
            constraints.add(res);
        }

        //stock constains
        for(Stock s : stockList){
            String sto = "";
            for(UnitOutlay u : s.getUnitOutlayList()){
                sto += u.getValue()+u.getName() +" + ";
            }
            sto = sto.substring(0, sto.length() - 3);
            sto += " <= " + s.getMaxProduction();
            System.out.println(sto);
            constraints.add(sto);
        }

        LP lp = new LP(constraints);
        SolutionType solution_type=lp.resolve();

        if(solution_type==SolutionType.OPTIMUM) {
            Solution soluzione=lp.getSolution();
            for(Variable var:soluzione.getVariables()) {
                SscLogger.log("Variable name :"+var.getName() + " value :"+var.getValue());
            }
            SscLogger.log("Value:"+soluzione.getOptimumValue());
        }


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

    public void onRestrictionLimitEdit(TableColumn.CellEditEvent<Restriction, Double> event) {
        Restriction restriction = tableViewRestriction.getSelectionModel().getSelectedItem();
        restriction.setLimit(event.getNewValue());
    }

    public void onProblemMaxAmountEdit(TableColumn.CellEditEvent<Problem, Integer> stockStringCellEditEvent) {
        Problem problem = tableViewProblem.getSelectionModel().getSelectedItem();
        problem.setMaxAmount(stockStringCellEditEvent.getNewValue());
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
        col_restrictionSign.setCellValueFactory(new PropertyValueFactory<>("comboBoxSign"));
        col_restrictionLimit.setCellValueFactory(new PropertyValueFactory<>("limit"));
        col_problemMaxAmount.setCellValueFactory(new PropertyValueFactory<>("maxAmount"));

        col_stockName.setCellFactory(TextFieldTableCell.forTableColumn());
        col_stockMaxProduction.setCellFactory(TextFieldTableCell.<Stock, Integer>forTableColumn(new IntegerStringConverter()));
        col_uoName.setCellFactory(TextFieldTableCell.forTableColumn());
        col_uoVal.setCellFactory(TextFieldTableCell.<UnitOutlay, Double>forTableColumn(new DoubleStringConverter()));
        col_problemName.setCellFactory(TextFieldTableCell.forTableColumn());
        col_problemPrice.setCellFactory(TextFieldTableCell.<Problem, Double>forTableColumn(new DoubleStringConverter()));
        col_restrictionLimit.setCellFactory(TextFieldTableCell.<Restriction, Double>forTableColumn(new DoubleStringConverter()));
        col_problemMaxAmount.setCellFactory(TextFieldTableCell.<Problem, Integer>forTableColumn(new IntegerStringConverter()));

        tableViewStock.setItems(stockList);
        tableViewProblem.setItems(problemList);
        tableViewRestriction.setItems(restrictionList);
    }

}
