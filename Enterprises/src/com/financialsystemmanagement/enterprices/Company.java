package com.financialsystemmanagement.enterprices;

import com.financialsystemmanagement.users.TPSpecialist;

public class Company extends Enterprise {
    private TPSpecialist Specialist;
    private int сompanyCapital = 200000;
    private int companyId;


    public Company(String EnterpriseType, String EnterpriseName, String UNP, String Address, int companyId){
        super(EnterpriseType, EnterpriseName, UNP, Address);
        this.Specialist= new TPSpecialist("Specialist", "123", companyId);
        this.companyId = companyId;
    }

    public int getСompanyCapital() {
        return сompanyCapital;
    }

    public void addСompanyCapital(int сompanyCapital) {
        this.сompanyCapital += сompanyCapital;
    }

    public void removeСompanyCapital(int сompanyCapital) {
        this.сompanyCapital -= сompanyCapital;
    }

}
