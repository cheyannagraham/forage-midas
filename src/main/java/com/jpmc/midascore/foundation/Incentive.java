package com.jpmc.midascore.foundation;

public class Incentive {
    private double amount;


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Incentive{" +
                "amount=" + amount +
                '}';
    }
}
