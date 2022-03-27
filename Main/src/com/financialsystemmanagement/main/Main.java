package com.financialsystemmanagement.main;

import com.financialsystemmanagement.database.Database;
import com.financialsystemmanagement.enterprices.Bank;
import com.financialsystemmanagement.interfaces.IBank;
import com.financialsystemmanagement.interfaces.IClient;
import com.financialsystemmanagement.repositories.BankRepository;
import com.financialsystemmanagement.repositories.ClientRepository;
import com.financialsystemmanagement.users.BankClient;

import java.io.*;
import java.util.List;
import java.util.Scanner;

import static com.financialsystemmanagement.main.UI.*;


public class Main {

    public static void main(String[] args) throws IOException {

        Scanner in = new Scanner(System.in);
        String Banks = "/home/arkady/dev/java/FinancialSystemManagement/" +
                "FinancialSystemManagement/db/Banks.txt";
        String Clients = "/home/arkady/dev/java/FinancialSystemManagement/" +
                "FinancialSystemManagement/db/Clients.txt";
        String Personal = "/home/arkady/dev/java/FinancialSystemManagement/" +
                "FinancialSystemManagement/db/Personal.txt";
        Database db = new Database(Banks, Clients, Personal);
        IBank bankRP = new BankRepository(db);
        IClient clientRP = new ClientRepository(db);
        List<String> banks = db.loadFromBank();
        List<String> clients = db.loadFromClients();
        String choice;
        boolean isCanceled = false;

        while(true){
            mainMenu();
            choice = in.nextLine();
            switch (choice) {
                case "1":
                    showAllBanks(banks, db);
                    break;
                case "2":
                    showAllClients(clients, db);
                    break;
                case "4": {
                    int id = choosingBank(banks, bankRP, db);
                    bankStartLogInMenu();
                    BankClient loginClient;
                    while (true) {
                        choice = in.nextLine();
                        if(choice.equals("1") || choice.equals("2") || choice.equals("3")){
                            break;
                        }
                    }
                    if(choice.equals("1")) {
                        while (true) {
                            loginClient = bankLoginMenu(clients, clientRP, bankRP.findBankById(banks, id));
                            if (loginClient != null) {
                                System.out.println("Welcome " + loginClient.getPersonalName() + "!");
                                break;
                            }
                        }
                    } else if(choice.equals("2")){
                        loginClient = clientRP.singUpUser();
                        clientRP.addClient(loginClient, clients);
                        db.saveToClients(clients);
                    } else {
                        for (int a: getBankClientsList(bankRP.findBankById(banks, id), clients, db)) {
                            clientRP.findClientById(clients, a).clientInfo();
                        }
                    }
                }
                default:
                    System.out.println("Enter correct symbol!");
            }
        }
    }
}