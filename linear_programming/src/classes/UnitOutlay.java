package classes;

public class UnitOutlay {
    String name;
    double value;
    int amount;

    public UnitOutlay(String name) {
        this.name = name;
        this.value = -1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
