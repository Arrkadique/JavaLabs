package com.financialsystemmanagement.users;

public class BankOperator extends User{
    private boolean isCanceled = false;

    public BankOperator(String personalName, String password, int bankId, boolean isCanceled){
        super(personalName, password, bankId);
        this.isCanceled = isCanceled;
    }

    public boolean getIsCancel(){
        return isCanceled;
    }

    public void confirmRequest(){

    }
}
