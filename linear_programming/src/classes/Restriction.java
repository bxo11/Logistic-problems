package classes;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

public class Restriction {
    String variable;
    ComboBox comboBoxSign;
    String limit;

    public Restriction(String variable) {
        this.variable = variable;
        this.limit = "";
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

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }
}
