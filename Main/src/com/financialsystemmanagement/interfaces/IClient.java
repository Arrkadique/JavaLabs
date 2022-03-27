package com.financialsystemmanagement.interfaces;

import com.financialsystemmanagement.users.BankClient;

import java.io.IOException;
import java.util.List;

public interface IClient {
    public BankClient findClientById(List<String> lines, int id);
    public BankClient findClientByName(List<String> lines, String name);
    public void addClient(BankClient client, List<String> lines);
    public BankClient singUpUser()throws IOException;
}
