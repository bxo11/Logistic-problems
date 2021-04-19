package app;

import app.classes.TransportationProblem;
import app.classes.Variable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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
    public TextField indvProfits11;
    @FXML
    public TextField indvProfits12;
    @FXML
    public TextField indvProfits21;
    @FXML
    public TextField indvProfits22;

    @FXML
    public TextField optTransport11;
    @FXML
    public TextField optTransport12;
    @FXML
    public TextField optTransport21;
    @FXML
    public TextField optTransport22;

    @FXML
    private Text income;
    @FXML
    private Text middleman_profit;
    @FXML
    private Text total_cost;
    @FXML
    public Text errorField;

    private final TransportationProblem operationObject = new TransportationProblem(2 + 1, 2 + 1);



    public void computeButtonOnClicked() throws InterruptedException {
        try {
            errorField.setText("");
            operationObject.setStock(Double.parseDouble(supply1.getText()), 0);
            operationObject.setStock(Double.parseDouble(supply2.getText()), 1);
            operationObject.setRequired(Double.parseDouble(demand1.getText()), 0);
            operationObject.setRequired(Double.parseDouble(demand2.getText()), 1);

            operationObject.setUnit_profit(Double.parseDouble(x11.getText()), 0, 0);
            operationObject.setUnit_profit(Double.parseDouble(x12.getText()), 0, 1);
            operationObject.setUnit_profit(Double.parseDouble(x21.getText()), 1, 0);
            operationObject.setUnit_profit(Double.parseDouble(x22.getText()), 1, 1);


            int stockSum = 0;
            int requiredSum = 0;
            double t_cost = 0.0;
            double local_transportation_cost = 0.0;
            double local_buy_cost = 0.0;
            double local_profit = 0.0;


            for (int i = 0; i < operationObject.getStockSize() - 1; ++i) {
                stockSum += operationObject.getStock(i);
            }
            for (int i = 0; i < operationObject.getRequiredSize() - 1; ++i) {
                requiredSum += operationObject.getRequired(i);
            }


            operationObject.setStock(stockSum, operationObject.getStockSize() - 1);
            operationObject.setRequired(requiredSum, operationObject.getRequiredSize() - 1);

            operationObject.setPurchasePrice(Double.parseDouble(pPrice1.getText()), 0);
            operationObject.setPurchasePrice(Double.parseDouble(pPrice2.getText()), 1);

            operationObject.setSellPrice(Double.parseDouble(sPrice1.getText()), 0);
            operationObject.setSellPrice(Double.parseDouble(sPrice2.getText()), 1);


            operationObject.maximumProfitRule();

            indvProfits11.setText("0");
            indvProfits12.setText("0");
            indvProfits21.setText("0");
            indvProfits22.setText("0");
            optTransport11.setText("0");
            optTransport12.setText("0");
            optTransport21.setText("0");
            optTransport22.setText("0");

            for (int i=0;i<2;i++ ){
                for (int j=0;j<2;j++ ){
                    if (i == 0 && j == 0) {
                        indvProfits11.setText(Double.toString(operationObject.getUnit_profit()[0][0]));
                    }
                    if (i == 0 && j == 1) {
                        indvProfits12.setText(Double.toString(operationObject.getUnit_profit()[0][1]));
                    }
                    if (i == 1 && j == 0) {
                        indvProfits21.setText(Double.toString(operationObject.getUnit_profit()[1][0]));
                    }
                    if (i == 1 && j == 1) {
                        indvProfits22.setText(Double.toString(operationObject.getUnit_profit()[1][1]));
                    }
                }
            }

            for (Variable v : operationObject.getFeasible()) {

                if (v.getStock() == 0 && v.getRequired() == 0) {
                    optTransport11.setText(Double.toString(v.getValue()));
                    local_transportation_cost += operationObject.getTransportation_cost()[0][0] * v.getValue();
                    local_buy_cost += v.getValue() * operationObject.getPurchasePrice()[0];
                    local_profit += v.getValue() * operationObject.getSellPrice()[0];
                }
                if (v.getStock() == 0 && v.getRequired() == 1) {
                    optTransport12.setText(Double.toString(v.getValue()));
                    local_transportation_cost += operationObject.getTransportation_cost()[0][1] * v.getValue();
                    local_buy_cost += v.getValue() * operationObject.getPurchasePrice()[0];
                    local_profit += v.getValue() * operationObject.getSellPrice()[1];
                }
                if (v.getStock() == 1 && v.getRequired() == 0) {
                    optTransport21.setText(Double.toString(v.getValue()));
                    local_transportation_cost += operationObject.getTransportation_cost()[1][0] * v.getValue();
                    local_buy_cost += v.getValue() * operationObject.getPurchasePrice()[1];
                    local_profit += v.getValue() * operationObject.getSellPrice()[0];
                }
                if (v.getStock() == 1 && v.getRequired() == 1) {
                    optTransport22.setText(Double.toString(v.getValue()));
                    local_transportation_cost += operationObject.getTransportation_cost()[1][1] * v.getValue();
                    local_buy_cost += v.getValue() * operationObject.getPurchasePrice()[1];
                    local_profit += v.getValue() * operationObject.getSellPrice()[1];
                }
            }

                t_cost = local_transportation_cost + local_buy_cost;
                total_cost.setText(local_transportation_cost+" + "+local_buy_cost+" = "+ Double.toString(t_cost));
                income.setText(String.valueOf(local_profit));
                middleman_profit.setText(Double.toString(operationObject.getSolution()));
            } catch(Exception ex) {
                errorField.setText("");
                Thread.sleep(500);
                errorField.setText("Insufficiently little data or incorrectly given.");
            }
        }
    }

