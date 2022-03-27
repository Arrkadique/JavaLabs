package com.financialsystemmanagement.enterprices;

import com.financialsystemmanagement.users.BankClient;

import java.util.List;

public class Bank extends Enterprise {
    private int bankId;
    private List<Integer> clientsList;
    private int bikOfBank;


    public Bank(String enterpriseType, String enterpriseName, String unp,
                String address, int bikOfBank, int bankId, List<Integer> clientsList){
        super(enterpriseType, enterpriseName, unp, address);
        this.bikOfBank = bikOfBank;
        this.bankId = bankId;
        this.clientsList = clientsList;
    }

    public List<Integer> getClientsList() {
        return clientsList;
    }

    public int getBankId() {
        return bankId;
    }

    public void AddClient(BankClient Client){
    }

    public void ShowClients(){
    }

    public void ShowAccounts(){
    }

    public void showInfo(){
        System.out.println(bankId + " " + EnterpriseInfo());
    }

}
