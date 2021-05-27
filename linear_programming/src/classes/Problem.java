package classes;

import it.ssc.log.SscLogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Problem {
    String name;
    static int iter = 1;
    double amount;
    double price;

    public Problem() {
        this.name = "X" + iter;
        this.amount = 1;
        this.price = 1;

        iter++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
