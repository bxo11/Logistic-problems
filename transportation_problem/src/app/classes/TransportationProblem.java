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
    double[][] unit_profit;
    double[][] transportation_cost;
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
        unit_profit = new double[stockSize][requiredSize];
        transportation_cost = new double[stockSize][requiredSize];

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

    public void setUnit_profit(double value, int stock, int required) {
        unit_profit[stock][required] = value;
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

    public double[][] getUnit_profit() {
        return unit_profit;
    }

    public double[][] getTransportation_cost() {
        return transportation_cost;
    }

    public double[] getPurchasePrice() {
        return purchasePrice;
    }

    public double[] getSellPrice() {
        return sellPrice;
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

    public double maximumProfitRule() {
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
                        if (unit_profit[m][n] > maxCost.getValue()) {
                            maxCost.setStock(m);
                            maxCost.setRequired(n);
                            maxCost.setValue(unit_profit[m][n]);
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
        for(int i=0; i<transportation_cost.length; i++)
            for(int j=0; j<transportation_cost[i].length; j++)
                transportation_cost[i][j]= unit_profit[i][j];

        for (int m = 0; m < stockSize - 1; m++) {
            for (int n = 0; n < requiredSize - 1; n++) {
                unit_profit[m][n] = sellPrice[n] - purchasePrice[m] - unit_profit[m][n];
            }
        }
        return 0;
    }

    public double getSolution() {
        double result = 0;
        for (Variable x : feasible) {
            result += x.getValue() * unit_profit[x.getStock()][x.getRequired()];
        }

        return result;

    }
}
