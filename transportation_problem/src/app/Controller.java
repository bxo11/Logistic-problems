package app;

import app.classes.TransportationProblem;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.fxml.FXML;

import java.util.Arrays;

public class Controller {
    @FXML
    public TextField podaz1;
    @FXML
    public TextField podaz2;
    @FXML
    public TextField popyt1;
    @FXML
    public TextField popyt2;
    @FXML
    public TextField x11;
    @FXML
    public TextField x12;
    @FXML
    public TextField x21;
    @FXML
    public TextField x22;
    @FXML
    private Text result;
    @FXML
    private TextField y11;
    @FXML
    private TextField y12;
    @FXML
    private TextField y21;
    @FXML
    private TextField y22;

    private final TransportationProblem operationObject = new TransportationProblem(2, 2);


    public void liczButtonOnClicked(){
        operationObject.setStock(Double.parseDouble(podaz1.getText()), 0);
        operationObject.setStock(Double.parseDouble(podaz2.getText()), 1);
        operationObject.setRequired(Double.parseDouble(popyt1.getText()), 0);
        operationObject.setRequired(Double.parseDouble(popyt2.getText()), 1);

        operationObject.setCost(Double.parseDouble(x11.getText()), 0, 0);
        operationObject.setCost(Double.parseDouble(x12.getText()), 0, 1);
        operationObject.setCost(Double.parseDouble(x21.getText()), 1, 0);
        operationObject.setCost(Double.parseDouble(x22.getText()), 1, 1);

        operationObject.leastCostRule();
        result.setText(String.valueOf(operationObject.getSolution()));

        String list = Arrays.toString(operationObject.feasible.toArray());
        y11.setText(String.valueOf(operationObject.feasible.get(0)));
        y12.setText(String.valueOf(operationObject.feasible.get(1)));
        y21.setText(String.valueOf(operationObject.feasible.get(2)));
        y22.setText(String.valueOf(operationObject.feasible.get(3)));
    }
}
