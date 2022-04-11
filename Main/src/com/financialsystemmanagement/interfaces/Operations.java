package com.financialsystemmanagement.interfaces;

import com.financialsystemmanagement.users.BankClient;

import java.io.IOException;

public interface Operations {
    void showInstallments(Client client) throws IOException;
    void takeInstallment(BankClient bankClient) throws IOException;
    void showCredits(Client client) throws IOException;
    void takeCredit(BankClient bankClient) throws IOException;
    void sortChanges() throws IOException;
}
