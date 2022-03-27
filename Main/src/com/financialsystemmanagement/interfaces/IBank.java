package com.financialsystemmanagement.interfaces;

import com.financialsystemmanagement.enterprices.Bank;

import java.util.List;

public interface IBank {
    public Bank findBankById(List<String> lines, int id);
}
