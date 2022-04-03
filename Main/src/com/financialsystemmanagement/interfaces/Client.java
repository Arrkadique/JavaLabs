package com.financialsystemmanagement.interfaces;

import com.financialsystemmanagement.enterprices.Banks;
import com.financialsystemmanagement.users.BankClient;

import java.io.IOException;

public interface Client {
    BankClient findClientById(int id)throws IOException;
    BankClient findClientByName(String name) throws IOException;
    BankClient singUpUser(int bankId)throws IOException;
    void makeTransfer(BankClient bankClient1, int bankId) throws IOException;
    void moneyWithdrawal(BankClient bankClient) throws IOException;
    void autoTransfer(BankClient bankClient1, BankClient bankClient2, int sum) throws IOException;
    boolean isInBank(BankClient bankClient, Banks banks);
}
