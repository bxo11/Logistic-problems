package classes;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

public class Restriction {
    String variable;
    ComboBox sign;
    double lowerLimit;
    double upperLimit;

    public Restriction(String variable) {
        this.variable = variable;
        this.lowerLimit = -1;
        this.upperLimit = -1;
        this.sign = new ComboBox(FXCollections.observableArrayList(ConstrainOperators.values()));
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public ComboBox getSign() {
        return sign;
    }

    public void setSign(ComboBox sign) {
        this.sign = sign;
    }

    public double getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(double lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public double getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(double upperLimit) {
        this.upperLimit = upperLimit;
    }
}
