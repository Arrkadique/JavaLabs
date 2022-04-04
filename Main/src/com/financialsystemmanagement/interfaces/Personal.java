package com.financialsystemmanagement.interfaces;

import com.financialsystemmanagement.users.BankManager;
import com.financialsystemmanagement.users.BankOperator;

import java.io.IOException;
import java.util.List;

public interface Personal {
    void showTransfers(List<String> lines, Client client) throws IOException;
    void cancelManagerAction(int bankId, Client client) throws IOException;
    void cancelOperatorAction(int bankId, Client client) throws IOException;
    void cancelAllActions(int bankId, Client client) throws IOException;
    BankOperator findOperatorByBankId(int bankId) throws IOException;
    BankManager findManagerByBankId(int bankId) throws IOException;
    void confirmCredit(Client client) throws IOException;
}
