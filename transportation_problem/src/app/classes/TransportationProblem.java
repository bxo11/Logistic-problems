/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.classes;

import java.util.LinkedList;

/**
 * @author amjad
 */
public class TransportationProblem {


    double[] required;
    double[] stock;
    double[][] cost;
    LinkedList<Variable> feasible = new LinkedList<Variable>();

    int stockSize;
    int requiredSize;

    double[] purchasePrice;
    double[] sellPrice;

    public TransportationProblem(int stockSize, int requiredSize) {
        this.stockSize = stockSize;
        this.requiredSize = requiredSize;

        stock = new double[stockSize];
        required = new double[requiredSize];
        cost = new double[stockSize][requiredSize];

        purchasePrice = new double[stockSize];
        sellPrice = new double[requiredSize];

        for (int i = 0; i < (requiredSize + stockSize - 1); i++)
            feasible.add(new Variable());

    }

    public void setStock(double value, int index) {
        stock[index] = value;
    }

    public double getStock(int index) {
        return stock[index];
    }

    public void setRequired(double value, int index) {
        required[index] = value;
    }

    public double getRequired(int index) {
        return required[index];
    }

    public void setPurchasePrice(double value, int index) {
        purchasePrice[index] = value;
    }

    public void setSellPrice(double value, int index) {
        sellPrice[index] = value;
    }

    public void setCost(double value, int stock, int required) {
        cost[stock][required] = value;
    }

    public int getStockSize() {
        return stockSize;
    }

    public int getRequiredSize() {
        return requiredSize;
    }

    public LinkedList<Variable> getFeasible() {
        return feasible;
    }

    /**
     * initializes the feasible solution list using the North-West Corner
     *
     * @return time elapsed
     */

    public double northWestCorner() {
        long start = System.nanoTime();

        double min;
        int k = 0; //feasible solutions counter

        //isSet is responsible for annotating cells that have been allocated
        boolean[][] isSet = new boolean[stockSize][requiredSize];
        for (int j = 0; j < requiredSize; j++)
            for (int i = 0; i < stockSize; i++)
                isSet[i][j] = false;

        //the for loop is responsible for iterating in the 'north-west' manner
        for (int j = 0; j < requiredSize; j++)
            for (int i = 0; i < stockSize; i++)
                if (!isSet[i][j]) {

                    //allocating stock in the proper manner
                    min = Math.min(required[j], stock[i]);

                    feasible.get(k).setRequired(j);
                    feasible.get(k).setStock(i);
                    feasible.get(k).setValue(min);
                    k++;

                    required[j] -= min;
                    stock[i] -= min;

                    //allocating null values in the removed row/column
                    if (stock[i] == 0)
                        for (int l = 0; l < requiredSize; l++)
                            isSet[i][l] = true;
                    else
                        for (int l = 0; l < stockSize; l++)
                            isSet[l][j] = true;
                }
        return (System.nanoTime() - start) * 1.0e-9;
    }

    /**
     * initializes the feasible solution list using the Least Cost Rule
     * <p>
     * it differs from the North-West Corner rule by the order of candidate cells
     * which is determined by the corresponding cost
     *
     * @return double: time elapsed
     */

    public double leastCostRule() {
        calculateProfits();

        long start = System.nanoTime();

        double min;
        int k = 0; //feasible solutions counter

        //isSet is responsible for annotating cells that have been allocated
        boolean[][] isSet = new boolean[stockSize][requiredSize];
        for (int j = 0; j < requiredSize; j++)
            for (int i = 0; i < stockSize; i++)
                isSet[i][j] = false;

        int i = 0, j = 0;
        Variable maxCost = new Variable();

        //this will loop is responsible for candidating cells by their least cost
        while (k < (stockSize + requiredSize - 1)) {

            maxCost.setValue(-Double.MAX_VALUE);
            //picking up the least cost cell
            for (int m = 0; m < stockSize; m++)
                for (int n = 0; n < requiredSize; n++)
                    if (!isSet[m][n])
                        if (cost[m][n] > maxCost.getValue()) {
                            maxCost.setStock(m);
                            maxCost.setRequired(n);
                            maxCost.setValue(cost[m][n]);
                        }

            i = maxCost.getStock();
            j = maxCost.getRequired();

            //allocating stock in the proper manner
            min = Math.min(required[j], stock[i]);

            feasible.get(k).setRequired(j);
            feasible.get(k).setStock(i);
            feasible.get(k).setValue(min);
            k++;

            required[j] -= min;
            stock[i] -= min;

            //allocating null values in the removed row/column
            if (stock[i] == 0)
                for (int l = 0; l < requiredSize; l++)
                    isSet[i][l] = true;
            else
                for (int l = 0; l < stockSize; l++)
                    isSet[l][j] = true;

        }

        return (System.nanoTime() - start) * 1.0e-9;

    }

    public double calculateProfits() {
        for (int m = 0; m < stockSize - 1; m++) {
            for (int n = 0; n < requiredSize - 1; n++) {
                cost[m][n] = sellPrice[n] - purchasePrice[m] - cost[m][n];
            }
        }
        return 0;
    }

    public double getSolution() {
        double result = 0;
        for (Variable x : feasible) {
            result += x.getValue() * cost[x.getStock()][x.getRequired()];
        }

        return result;

    }


    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) throws IOException {
//        // TODO code application logic here
//
//        Scanner scanner = new Scanner(System.in);
//
//        int s = 2;
//        int r = 3;
//        TransportationProblem test = new TransportationProblem(s + 1, r + 1);
//
//        test.setStock(20, 0);
//        test.setStock(30, 1);
//
//        test.setRequired(10, 0);
//        test.setRequired(28, 1);
//        test.setRequired(27, 2);
//
//        int stockSum = 0;
//        int requiredSum = 0;
//        for (int i = 0; i < test.stockSize -1; i++) {
//            stockSum += test.stock[i];
//        }
//        for (int i = 0; i < test.requiredSize -1; i++) {
//            requiredSum += test.required[i];
//        }
//        test.setStock(requiredSum, test.stockSize -1);
//        test.setRequired(requiredSum, test.requiredSize -1);
//
//        test.setPurchasePrice(10, 0);
//        test.setPurchasePrice(12, 1);
//
//        test.setSellPrice(30, 0);
//        test.setSellPrice(25, 1);
//        test.setSellPrice(30, 2);
//
//        test.setCost(8, 0, 0);
//        test.setCost(14, 0, 1);
//        test.setCost(17, 0, 2);
//
//        test.setCost(12, 1, 0);
//        test.setCost(9, 1, 1);
//        test.setCost(19, 1, 2);
//
//        test.leastCostRule();
//
//        for (Variable t : test.feasible) {
//            System.out.println(t);
//        }
//
//        System.out.println("Target function: " + test.getSolution());
//
//    }


}
