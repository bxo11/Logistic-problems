package app;

import app.classes.TransportationProblem;
import app.classes.Variable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;



public class Controller {
    @FXML
    public TextField supply1;
    @FXML
    public TextField supply2;
    @FXML
    public TextField demand1;
    @FXML
    public TextField demand2;

    @FXML
    public TextField pPrice1;
    @FXML
    public TextField pPrice2;
    @FXML
    public TextField sPrice1;
    @FXML
    public TextField sPrice2;

    @FXML
    public TextField x11;
    @FXML
    public TextField x12;
    @FXML
    public TextField x21;
    @FXML
    public TextField x22;
    @FXML
    public TableView optTransTable;
    @FXML
    public TableView unitCostTable;
    @FXML
    public TableColumn fromColumn_ot;
    @FXML
    public TableColumn toColumn_ot;
    @FXML
    public TableColumn valueColumn_ot;
    @FXML
    public TableColumn fromColumn_uc;
    @FXML
    public TableColumn toColumn_uc;
    @FXML
    public TableColumn costColumn_ut;

    @FXML
    private Text profit;
    @FXML
    private Text total_income;
    @FXML
    private Text total_cost;


    private final TransportationProblem operationObject = new TransportationProblem(2 + 1, 2 + 1);

    public ObservableList<Variable> optimalTransportation = FXCollections.observableArrayList();



    public void computeButtonOnClicked() {
        operationObject.setStock(Double.parseDouble(supply1.getText()), 0);
        operationObject.setStock(Double.parseDouble(supply2.getText()), 1);
        operationObject.setRequired(Double.parseDouble(demand1.getText()), 0);
        operationObject.setRequired(Double.parseDouble(demand2.getText()), 1);

        operationObject.setCost(Double.parseDouble(x11.getText()), 0, 0);
        operationObject.setCost(Double.parseDouble(x12.getText()), 0, 1);
        operationObject.setCost(Double.parseDouble(x21.getText()), 1, 0);
        operationObject.setCost(Double.parseDouble(x22.getText()), 1, 1);


        int stockSum    = 0;
        int requiredSum = 0;

        for (int i = 0; i < operationObject.getStockSize() - 1; ++i) {
            stockSum += operationObject.getStock(i);
        }
        for (int i = 0; i < operationObject.getRequiredSize() - 1; ++i) {
            requiredSum += operationObject.getRequired(i);
        }


        operationObject.setStock(requiredSum, operationObject.getStockSize() - 1);
        operationObject.setRequired(requiredSum, operationObject.getRequiredSize() - 1);

        operationObject.setPurchasePrice(Double.parseDouble(pPrice1.getText()), 0);
        operationObject.setPurchasePrice(Double.parseDouble(pPrice2.getText()), 1);

        operationObject.setSellPrice(Double.parseDouble(sPrice1.getText()), 0);
        operationObject.setSellPrice(Double.parseDouble(sPrice2.getText()), 1);



        operationObject.leastCostRule();
        profit.setText(String.valueOf(operationObject.getSolution()));

        for (Variable v : operationObject.getFeasible()) {
            v.setStock(v.getStock() + 1);
            v.setRequired(v.getRequired() + 1);
            optimalTransportation.add(v);
        }

        fromColumn_ot.setCellValueFactory(new PropertyValueFactory<>("stock"));
        toColumn_ot.setCellValueFactory(new PropertyValueFactory<>("required"));
        valueColumn_ot.setCellValueFactory(new PropertyValueFactory<>("value"));
        optTransTable.setItems(optimalTransportation);
    }
}
