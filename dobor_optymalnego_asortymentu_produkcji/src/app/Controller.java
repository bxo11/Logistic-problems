package app;
import classes.Stock;
import classes.UnitOutlay;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {

    public TableColumn<Stock, String> col_stockName;
    public TableColumn<UnitOutlay, String> col_uoName;
    public TableColumn<UnitOutlay, Double> col_uoVal;
    public TableView<Stock> tableViewStock;
    public TableView<UnitOutlay> tableViewUnitOutlay;
    ObservableList<Stock> stockList = FXCollections.observableArrayList( new Stock("tasma"));
    ObservableList<UnitOutlay> uoList = FXCollections.observableArrayList( new UnitOutlay("W1",10), new UnitOutlay("W2",12));



    @FXML
    public void initialize() {

        col_stockName.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_uoName.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_uoVal.setCellValueFactory(new PropertyValueFactory<>("value"));
        tableViewStock.setItems(stockList);
        tableViewUnitOutlay.setItems(uoList);
    }

}