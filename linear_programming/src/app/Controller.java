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
import javafx.scene.text.Text;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import lpsolve.LpSolve;
import lpsolve.LpSolveException;



public class Controller {

    public TableColumn<Stock, String> col_stockName;
    public TableColumn<Stock, Integer> col_stockMaxProduction;
    public TableColumn<UnitOutlay, String> col_uoName;
    public TableColumn<UnitOutlay, Double> col_uoVal;
    public TableColumn<Problem, String> col_problemName;
    public TableColumn<Problem, Double> col_problemPrice;
    public TableColumn<Problem, Integer> col_problemMaxAmount;
    public TableView<Stock> tableViewStock;
    public TableView<UnitOutlay> tableViewUnitOutlay;
    public TableView<Problem> tableProblem;
    public Text income;
    ObservableList<Problem> problemList = FXCollections.observableArrayList();
    ObservableList<Stock> stockList = FXCollections.observableArrayList();
    ObservableList<UnitOutlay> uoList = FXCollections.observableArrayList();

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

    public void buttonSolve() {
        try {
            LpSolve lp;
            int nCol, j, ret = 0;
            nCol = problemList.size();

            int[] colno = new int[nCol];
            double[] row = new double[nCol];

            lp = LpSolve.makeLp(0, nCol);
            if(lp.getLp() == 0)
                ret = 1;

            if(ret == 0) {
                j = 0;

                for (Problem problem: problemList) {
                    lp.setColName(++j, problem.getName());
                }
            }

            if(ret == 0) {
                lp.setAddRowmode(true);

                for (Stock stock: stockList) {
                    j = 0;
                    ObservableList<UnitOutlay> outlayList = stock.getUnitOutlayList();

                    for (int i = 0; i < nCol; ++i){
                        colno[j] = ++j;
                        row[j] = outlayList.get(j - 1).getValue();
                    }
                    lp.addConstraintex(j, row, colno, LpSolve.LE, stock.getMaxProduction());
                }
            }

            if(ret == 0) {
                j = 0;

                for (Problem problem: problemList) {
                    colno[0] = 1;
                    row[1] = 1;
                    lp.addConstraintex(j, row, colno, LpSolve.LE, problem.getMaxAmount());
                }
            }

            if(ret == 0) {
                lp.setAddRowmode(false);

                j = 0;

                for (Problem problem: problemList) {
                    colno[j] = ++j;
                    row[j] = problem.getPrice();
                }

                lp.setObjFnex(j, row, colno);
            }

            if(ret == 0) {
                lp.setMaxim();

                lp.setVerbose(LpSolve.IMPORTANT);

                ret = lp.solve();
            }

            if(ret == 0) {
                income.setText(String.valueOf(lp.getObjective()));

                lp.getVariables(row);
                for(j = 0; j < nCol; j++)
                    System.out.println(lp.getColName(j + 1) + ": " + row[j]);
            }

            if(lp.getLp() != 0)
                lp.deleteLp();
        }
        catch (LpSolveException e) {
            e.printStackTrace();
        }
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

    public void onStockMaxProductionEdit(TableColumn.CellEditEvent<Stock, Integer> stockStringCellEditEvent) {
        Stock stock = tableViewStock.getSelectionModel().getSelectedItem();
        stock.setMaxProduction(stockStringCellEditEvent.getNewValue());
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

    public void onProblemMaxAmountEdit(TableColumn.CellEditEvent<Problem, Integer> stockStringCellEditEvent) {
        Problem problem = tableProblem.getSelectionModel().getSelectedItem();
        problem.setMaxAmount(stockStringCellEditEvent.getNewValue());
    }

    public void onProblemPriceEdit(TableColumn.CellEditEvent<Problem, Double> stockStringCellEditEvent) {
        Problem problem = tableProblem.getSelectionModel().getSelectedItem();
        problem.setPrice(stockStringCellEditEvent.getNewValue());
    }

    @FXML
    public void initialize() {
        col_stockName.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_stockMaxProduction.setCellValueFactory(new PropertyValueFactory<>("maxProduction"));
        col_uoName.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_uoVal.setCellValueFactory(new PropertyValueFactory<>("value"));
        col_problemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_problemMaxAmount.setCellValueFactory(new PropertyValueFactory<>("maxAmount"));
        col_problemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        col_stockName.setCellFactory(TextFieldTableCell.forTableColumn());
        col_stockMaxProduction.setCellFactory(TextFieldTableCell.<Stock, Integer>forTableColumn(new IntegerStringConverter()));
        col_uoName.setCellFactory(TextFieldTableCell.forTableColumn());
        col_uoVal.setCellFactory(TextFieldTableCell.<UnitOutlay, Double>forTableColumn(new DoubleStringConverter()));
        col_problemName.setCellFactory(TextFieldTableCell.forTableColumn());
        col_problemMaxAmount.setCellFactory(TextFieldTableCell.<Problem, Integer>forTableColumn(new IntegerStringConverter()));
        col_problemPrice.setCellFactory(TextFieldTableCell.<Problem, Double>forTableColumn(new DoubleStringConverter()));

        tableViewStock.setItems(stockList);
        tableProblem.setItems(problemList);
    }

}