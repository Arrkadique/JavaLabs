package com.financialsystemmanagement.interfaces;

import com.financialsystemmanagement.enterprices.Banks;
import com.financialsystemmanagement.users.BankClient;

import java.io.IOException;

public interface Bank {
    Banks findBankById(int id)throws IOException;
    void addClientToBank(BankClient client, int bankId)throws IOException;
}
