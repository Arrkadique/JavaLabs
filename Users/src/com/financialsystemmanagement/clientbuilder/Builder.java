package com.financialsystemmanagement.clientbuilder;

import java.util.List;

public interface Builder {
    ClientBuilder setPersonalName(String name);
    ClientBuilder setUserId(int id);
    ClientBuilder setPassword(String password);
    ClientBuilder setPassportNumber(String passportNumber);
    ClientBuilder setIdentificationNumber(String identificationNumber);
    ClientBuilder setPhoneNumber(String phoneNumber);
    ClientBuilder setEmail(String email);
    ClientBuilder setBankList(List<Integer> list);
    ClientBuilder setMoneyCount(int moneyCount);
    ClientBuilder setBlocked(boolean isBlocked);
}
