package classes;

public class UnitOutlay {
    String name;
    double value;

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

}
