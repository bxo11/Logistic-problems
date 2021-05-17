package classes;

public class Problem {
    String name;
    int amount;
    double price;

    public Problem() {
        this.name = "...";
        this.amount = -1;
        this.price = -1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
