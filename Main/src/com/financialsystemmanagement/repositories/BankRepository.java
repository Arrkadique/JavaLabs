package com.financialsystemmanagement.repositories;

import com.financialsystemmanagement.database.Database;
import com.financialsystemmanagement.enterprices.Banks;
import com.financialsystemmanagement.interfaces.Bank;
import com.financialsystemmanagement.users.BankClient;

import java.io.IOException;
import java.util.List;

public class BankRepository implements Bank {
    private Database db;

    public BankRepository(Database db) {
        this.db = db;
    }

    public Banks findBankById(int id) throws IOException {
        List<String> lines = db.loadFromBank();
        for (String s: lines) {
            Banks a = db.deserializeBank(s);
            if(a.getBankId() == id){
                return a;
            }
        }
        System.out.println("null");
        return null;
    }

    public void addClientToBank(BankClient client, int bankId) throws IOException{
        List<String> fromBank = db.loadFromBank();
        Banks banks;
        for (String s: fromBank) {
            if(bankId == db.deserializeBank(s).getBankId()){
                banks = db.deserializeBank(s);
                banks.getClientsList().add(client.getUserId());
                fromBank.set(fromBank.indexOf(s), db.serializeBank(banks));
                System.out.println("ADDED");
            }
        }
        db.saveToBank(fromBank);
    }

}
