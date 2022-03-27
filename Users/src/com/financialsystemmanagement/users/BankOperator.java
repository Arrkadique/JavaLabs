package com.financialsystemmanagement.users;

public class BankOperator extends User{
    private boolean isCanceled = false;

    public BankOperator(String personalName, String password, int bankId){
        super(personalName, password, bankId);
    }

    public boolean getIsCancel(){
        return isCanceled;
    }

    public void confirmRequest(){

    }
}
