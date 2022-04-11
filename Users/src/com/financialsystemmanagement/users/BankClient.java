package com.financialsystemmanagement.users;

public class BankClient extends User{
    private final String passportNumber;
    private final String identificationNumber;
    private final String phoneNumber;
    private final String email;
    private final int bankId;
    private int moneyCount = 5000;
    private boolean isBlocked = false;

    public BankClient(int userId, String personalName, String password, String passportNumber, String identificationNumber,
                      String phoneNumber, String email, int moneyCount, int bankId, boolean isBlocked){
        super(personalName,password, userId);
        this.isBlocked = isBlocked;
        this.passportNumber = passportNumber;
        this.identificationNumber = identificationNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bankId = bankId;
        this.moneyCount = moneyCount;
    }

    public void clientInfo(){
        System.out.println(getUserId() + ". " + getPersonalName() + " " + getPhoneNumber() + " " + moneyCount);
    }

    public boolean getIsBlocked() {
        return isBlocked;
    }

    public int getBankId() {
        return bankId;
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
