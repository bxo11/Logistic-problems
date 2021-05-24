package classes;

public class Problem {
    String name;
    static int iter = 1;
    int amount;
    double price;
    int maxAmount;

    public Problem() {
        this.name = "W" + iter;
        this.amount = -1;
        this.price = -1;
        this.maxAmount = -1;

        iter++;
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

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }
}
