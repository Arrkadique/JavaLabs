package com.financialsystemmanagement.repositories;

import com.financialsystemmanagement.database.Database;
import com.financialsystemmanagement.enterprices.Bank;
import com.financialsystemmanagement.interfaces.IBank;

import java.util.List;

public class BankRepository implements IBank {
    private Database db;

    public BankRepository(Database db) {
        this.db = db;
    }

    public Bank findBankById(List<String> lines, int id){
        for (String s: lines) {
            Bank a = db.deserializeBank(s);
            if(a.getBankId() == id){
                return a;
            }
        }
        return null;
    }

}
