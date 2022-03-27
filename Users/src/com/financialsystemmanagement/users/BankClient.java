package com.financialsystemmanagement.users;

import java.util.List;

public class BankClient extends User{
    private String passportNumber;
    private String identificationNumber;
    private String phoneNumber;
    private String email;
    private List<Integer> banksList;
    private int moneyCount = 5000;

    public BankClient(int userId, String personalName, String password, String passportNumber, String identificationNumber,
                      String phoneNumber, String email, int moneyCount, List<Integer> banksList){
        super(personalName,password, userId);
        this.passportNumber = passportNumber;
        this.identificationNumber = identificationNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.banksList = banksList;
        this.moneyCount = moneyCount;
    }

    public BankClient(int userId, String personalName, String password, String passportNumber, String identificationNumber,
                      String phoneNumber, String email){
        super(personalName,password, userId);
        this.passportNumber = passportNumber;
        this.identificationNumber = identificationNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.banksList.add(0);
    }

    public void clientInfo(){
        System.out.println(getPersonalName() + " " + getPhoneNumber() + " " + moneyCount);
    }

    public List<Integer> getBanksList() {
        return banksList;
    }

    public int getMoneyCount() {
        return moneyCount;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void addMoney(int a){
        this.moneyCount += a;
    }

    public void removeMoney(int a){
        this.moneyCount -= a;
    }


}
