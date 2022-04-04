package com.financialsystemmanagement.operations;

public class Installment {
    private int userId;
    private int sum;
    private int terms;

    public Installment(int userId, int sum, int terms) {
        this.userId = userId;
        this.sum = sum;
        this.terms = terms;
    }

    public String getInstallmentInfo(){
        return " Sum: " + sum + " Term: " + terms;
    }

    public int getUserId() {
        return userId;
    }

    public int getSum() {
        return sum;
    }

    public int getTerms() {
        return terms;
    }
}
