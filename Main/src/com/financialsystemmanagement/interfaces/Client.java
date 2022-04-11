package com.financialsystemmanagement.interfaces;

import com.financialsystemmanagement.enterprices.Banks;
import com.financialsystemmanagement.users.BankClient;

import java.io.IOException;
import java.util.List;

public interface Client {
    BankClient findClientById(int id)throws IOException;
    List<BankClient> findClientByName(String name) throws IOException;
    BankClient singUpUser(int bankId)throws IOException;
    BankClient addBankAccount(BankClient bankClient) throws IOException;
    void makeTransfer(BankClient bankClient1, int bankId) throws IOException;
    void moneyWithdrawal(BankClient bankClient) throws IOException;
    void autoTransfer(BankClient bankClient1, BankClient bankClient2, int sum) throws IOException;
    boolean isInBank(List<BankClient> bankClient, Banks banks);
}
