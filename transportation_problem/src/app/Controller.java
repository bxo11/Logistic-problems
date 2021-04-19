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
    private Text profit;
    @FXML
    private Text total_income;
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

            operationObject.setCost(Double.parseDouble(x11.getText()), 0, 0);
            operationObject.setCost(Double.parseDouble(x12.getText()), 0, 1);
            operationObject.setCost(Double.parseDouble(x21.getText()), 1, 0);
            operationObject.setCost(Double.parseDouble(x22.getText()), 1, 1);


            int stockSum = 0;
            int requiredSum = 0;
            double total_local_cost = 0.0;


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


            for (Variable v : operationObject.getFeasible()) {

                if (v.getStock() == 0 && v.getRequired() == 0) {
                    indvProfits11.setText(Double.toString(operationObject.getCost()[0][0]));
                    optTransport11.setText(Double.toString(v.getValue()));
                    total_local_cost += operationObject.getCost()[0][0] * (Double.parseDouble(sPrice1.getText()) - Double.parseDouble(pPrice1.getText())) + Double.parseDouble(x11.getText());
                }
                if (v.getStock() == 0 && v.getRequired() == 1) {
                    indvProfits12.setText(Double.toString(operationObject.getCost()[0][1]));
                    optTransport12.setText(Double.toString(v.getValue()));
                    total_local_cost += operationObject.getCost()[0][1] * (Double.parseDouble(sPrice1.getText()) - Double.parseDouble(pPrice2.getText())) + Double.parseDouble(x12.getText());
                }
                if (v.getStock() == 1 && v.getRequired() == 0) {
                    indvProfits21.setText(Double.toString(operationObject.getCost()[1][0]));
                    optTransport21.setText(Double.toString(v.getValue()));
                    total_local_cost += operationObject.getCost()[1][0] * (Double.parseDouble(sPrice2.getText()) - Double.parseDouble(pPrice1.getText())) + Double.parseDouble(x21.getText());
                }
                if (v.getStock() == 1 && v.getRequired() == 1) {
                    indvProfits22.setText(Double.toString(operationObject.getCost()[1][1]));
                    optTransport22.setText(Double.toString(v.getValue()));
                    total_local_cost += operationObject.getCost()[1][1] * (Double.parseDouble(sPrice2.getText()) - Double.parseDouble(pPrice2.getText())) + Double.parseDouble(x22.getText());
                }
            }


                total_cost.setText(Double.toString(total_local_cost));
                total_income.setText(Double.toString(operationObject.getSolution()));
                profit.setText(String.valueOf(operationObject.getSolution() - total_local_cost));
            } catch(Exception ex) {
                errorField.setText("");
                Thread.sleep(500);
                errorField.setText("Insufficiently little data or incorrectly given.");
            }
        }
    }

