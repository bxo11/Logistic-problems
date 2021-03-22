package app;

import app.classes.TransportationProblem;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.Arrays;

public class Controller {
    @FXML
    public Text d1;
    @FXML
    public Text d2;
    @FXML
    public Text o1;
    @FXML
    public Text o2;
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
        operationObject.setStock(String.valueOf(d1), 0);
        operationObject.setStock(String.valueOf(d2), 1);
        operationObject.setRequired(String.valueOf(o1), 0);
        operationObject.setRequired(String.valueOf(o2), 1);

        operationObject.setCost(x11, 0, 0);
        operationObject.setCost(x12, 0, 1);
        operationObject.setCost(x21, 1, 0);
        operationObject.setCost(x22, 1, 1);

        operationObject.leastCostRule();
        result = (Text) Double.toString(operationObject.getSolution());

        ArrayList list = Arrays.toString(operationObject.feasible.toArray());
        y11 = operationObject.feasible.get(0);
        y12 = operationObject.feasible.get(1);
        y21 = operationObject.feasible.get(2);
        y22 = operationObject.feasible.get(3);
    }
}
