package com.financialsystemmanagement.clientbuilder;

import com.financialsystemmanagement.users.BankClient;

import java.util.ArrayList;
import java.util.List;

public class ClientBuilder implements Builder {
    private int userId;
    private String personalName;
    private String password;
    private String passportNumber;
    private String identificationNumber;
    private String phoneNumber;
    private String email;
    private List<Integer> banksList = new ArrayList<>();
    private int moneyCount = 5000;
    private boolean isBlocked = false;

    @Override
    public ClientBuilder setBlocked(boolean blocked) {
        isBlocked = blocked;
        return this;
    }

    @Override
    public ClientBuilder setBankList(List<Integer> list) {
        this.banksList = list;
        return this;
    }

    @Override
    public ClientBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public ClientBuilder setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
        return this;
    }

    @Override
    public ClientBuilder setMoneyCount(int moneyCount) {
        this.moneyCount = moneyCount;
        return this;
    }


    @Override
    public ClientBuilder setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
        return this;
    }

    @Override
    public ClientBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public ClientBuilder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public ClientBuilder setPersonalName(String personalName) {
        this.personalName = personalName;
        return this;
    }

    @Override
    public ClientBuilder setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public BankClient getResult(){
        return new BankClient(userId,personalName,password,passportNumber,identificationNumber,
                phoneNumber,email,moneyCount,banksList,isBlocked);
    }

}
