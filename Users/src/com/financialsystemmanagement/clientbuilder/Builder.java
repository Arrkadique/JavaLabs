package com.financialsystemmanagement.clientbuilder;

public interface Builder {
    ClientBuilder setPersonalName(String name);
    ClientBuilder setUserId(int id);
    ClientBuilder setPassword(String password);
    ClientBuilder setPassportNumber(String passportNumber);
    ClientBuilder setIdentificationNumber(String identificationNumber);
    ClientBuilder setPhoneNumber(String phoneNumber);
    ClientBuilder setEmail(String email);
    ClientBuilder setBankId(int list);
    ClientBuilder setMoneyCount(int moneyCount);
    ClientBuilder setBlocked(boolean isBlocked);
}
