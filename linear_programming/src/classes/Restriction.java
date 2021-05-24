package classes;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

public class Restriction {
    String variable;
    ComboBox comboBoxSign;
    double limit;

    public Restriction(String variable) {
        this.variable = variable;
        this.limit = -1;
        this.comboBoxSign = new ComboBox(FXCollections.observableArrayList("=","<=",">=","<",">"));
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public ComboBox getComboBoxSign() {
        return comboBoxSign;
    }

    public void setComboBoxSign(ComboBox comboBoxSign) {
        this.comboBoxSign = comboBoxSign;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }
}
