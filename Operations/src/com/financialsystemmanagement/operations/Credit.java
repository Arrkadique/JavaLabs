package com.financialsystemmanagement.operations;

public class Credit extends Installment{
    private int commission;
    private boolean isConfirmed;


    public Credit(int userId, int sum, int terms, int commission, boolean isConfirmed) {
        super(userId, sum, terms);
        this.isConfirmed = isConfirmed;
        this.commission = commission;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public String getCreditInfo(){
        return getInstallmentInfo() + " commission: " + commission + "% isConfirmed: " + isConfirmed;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public int getCommission() {
        return commission;
    }
}
