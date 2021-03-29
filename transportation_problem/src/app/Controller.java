package app;

import app.classes.TransportationProblem;
import app.classes.Variable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.Arrays;

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
    private Text profit;
    @FXML
    private Text total_income;
    @FXML
    private Text total_cost;

    @FXML
    private TextField p11;
    @FXML
    private TextField p12;
    @FXML
    private TextField p21;
    @FXML
    private TextField p22;
    @FXML
    private TextField t11;
    @FXML
    private TextField t12;
    @FXML
    private TextField t21;
    @FXML
    private TextField t22;

    private final TransportationProblem operationObject = new TransportationProblem(2, 2);

    public void computeButtonOnClicked(){
        operationObject.setStock(Double.parseDouble(supply1.getText()), 0);
        operationObject.setStock(Double.parseDouble(supply2.getText()), 1);
        operationObject.setRequired(Double.parseDouble(demand1.getText()), 0);
        operationObject.setRequired(Double.parseDouble(demand2.getText()), 1);

        operationObject.setCost(Double.parseDouble(x11.getText()), 0, 0);
        operationObject.setCost(Double.parseDouble(x12.getText()), 0, 1);
        operationObject.setCost(Double.parseDouble(x21.getText()), 1, 0);
        operationObject.setCost(Double.parseDouble(x22.getText()), 1, 1);

        operationObject.leastCostRule();
        profit.setText(String.valueOf(operationObject.getSolution()));

        for(Variable v : operationObject.feasible){
            if (v.getStock()==0 && v.getRequired()==0){
                p11.setText(String.valueOf(v.getValue()));
            }
            else if(v.getStock()==0 && v.getRequired()==1){
                p12.setText(String.valueOf(v.getValue()));
            }
            else if(v.getStock()==1 && v.getRequired()==0){
                p21.setText(String.valueOf(v.getValue()));
            }
            else if(v.getStock()==1 && v.getRequired()==1){
                p22.setText(String.valueOf(v.getValue()));
            }
        }
    }
}
