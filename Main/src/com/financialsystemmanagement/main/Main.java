package com.financialsystemmanagement.main;

import com.financialsystemmanagement.database.Database;
import com.financialsystemmanagement.interfaces.Bank;
import com.financialsystemmanagement.interfaces.Client;
import com.financialsystemmanagement.interfaces.Operations;
import com.financialsystemmanagement.interfaces.Personal;
import com.financialsystemmanagement.repositories.BankRepository;
import com.financialsystemmanagement.repositories.ClientRepository;
import com.financialsystemmanagement.repositories.OperationsRepository;
import com.financialsystemmanagement.repositories.PersonalRepository;
import com.financialsystemmanagement.users.BankClient;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static com.financialsystemmanagement.main.UI.bankClientMenu;
import static com.financialsystemmanagement.main.UI.bankLoginMenu;
import static com.financialsystemmanagement.main.UI.bankLoginPersonalMenu;
import static com.financialsystemmanagement.main.UI.bankStartLogInMenu;
import static com.financialsystemmanagement.main.UI.choosingBank;
import static com.financialsystemmanagement.main.UI.mainMenu;
import static com.financialsystemmanagement.main.UI.showAllBanks;
import static com.financialsystemmanagement.main.UI.showAllClients;


public class Main{

    public static void main(String[] args) throws IOException {

        Scanner in = new Scanner(System.in);
        Database db = new Database();
        Operations operationsRP = new OperationsRepository(db);
        Personal personalRP = new PersonalRepository(db);
        Bank bankRP = new BankRepository(db);
        Client clientRP = new ClientRepository(db);
        List<String> banks = db.loadFromBank();
        List<String> clients = db.loadFromClients();
        String choice;
        BankClient loginClient;
        int id;
        boolean isCanceled;

        while(true){
            mainMenu();
            personalRP.showTransfers(db.loadFromChanges(), clientRP);
            operationsRP.showInstallments(clientRP);
            choice = in.nextLine();
            switch (choice) {
                case "1":
                    showAllBanks(banks, db);
                    break;
                case "2":
                    showAllClients(db.loadFromClients(), db);
                    break;
                case "4": {
                    id = choosingBank(banks, bankRP, db);
                    while (true) {
                        isCanceled = false;
                        bankStartLogInMenu();
                        while (true) {
                            choice = in.nextLine();
                            if(choice.equals("1") || choice.equals("2") || choice.equals("3")
                                    || choice.equals("4")|| choice.equals("5")){
                                break;
                            }
                        }
                        switch (choice) {
                            case "1":
                                while (true) {
                                    loginClient = bankLoginMenu(clients, clientRP, bankRP.findBankById(id));
                                    if (loginClient != null) {
                                        System.out.println("Welcome " + loginClient.getPersonalName() + "!");
                                        break;
                                    }
                                }
                                while (true) {
                                    bankClientMenu();
                                    isCanceled = false;
                                    while (true) {
                                        choice = in.nextLine();
                                        if(choice.equals("1") || choice.equals("2") || choice.equals("3")
                                                || choice.equals("4")|| choice.equals("5")|| choice.equals("6")){
                                            break;
                                        }
                                    }
                                    switch (choice){
                                        case "1":{
                                            System.out.println("Making a transfer to:");
                                            for (int a : bankRP.findBankById(id).getClientsList()) {
                                                clientRP.findClientById(a).clientInfo();
                                            }
                                            clientRP.makeTransfer(loginClient, id);
                                        } break;
                                        case "2": {
                                            System.out.println("What sum do you need?");
                                            clientRP.moneyWithdrawal(loginClient);
                                        }break;
                                        case "5": loginClient.clientInfo();break;
                                        case "6": isCanceled = true;break;
                                        case "3":{
                                            operationsRP.takeInstallment(loginClient);
                                        }break;
                                        case "4":{
                                            operationsRP.takeCredit(loginClient);
                                        }break;
                                    }
                                    if(isCanceled) {
                                        choice = "0";
                                        break;
                                    }
                                }
                                break;
                            case "2":
                            {
                                String str = bankLoginPersonalMenu(bankRP.findBankById(id));
                                switch (str){
                                    case "1": {
                                        System.out.println("What would you like to do?\n" +
                                                "1. Show logs\n" +
                                                "2. Cancel actions");
                                        while (true){
                                            str = in.nextLine();
                                            if(str.equals("1") || str.equals("2")){
                                                break;
                                            }
                                            System.out.println("Try again!");
                                        }
                                        if(str.equals("2")){
                                            personalRP.cancelAllActions(id, clientRP);
                                        }
                                    }break;
                                    case "2": {
                                        System.out.println("What would you like to do?\n" +
                                                "1. Show transfers\n" +
                                                "2. Cancel action");
                                        while (true){
                                            str = in.nextLine();
                                            if(str.equals("1") || str.equals("2")){
                                                break;
                                            }
                                            System.out.println("Try again!");
                                        }
                                        if(str.equals("1"))
                                        personalRP.showTransfers(db.loadFromChanges(), clientRP);
                                        else {
                                            personalRP.cancelOperatorAction(id, clientRP);
                                            personalRP.findOperatorByBankId(id).setCanceled(true);
                                        }
                                        choice = "0";
                                    }break;
                                    case "3":{
                                        System.out.println("What would you like to do?\n" +
                                                "1. Show transfers\n" +
                                                "2. Cancel action\n" +
                                                "3. Confirm credit");
                                        while (true){
                                            str = in.nextLine();
                                            if(str.equals("1") || str.equals("2")
                                                    || str.equals("3")){
                                                break;
                                            }
                                            System.out.println("Try again!");
                                        }
                                        if(str.equals("1"))
                                            personalRP.showTransfers(db.loadFromChanges(), clientRP);
                                        else if(str.equals("2")){
                                            personalRP.cancelManagerAction(id, clientRP);
                                            personalRP.findManagerByBankId(id).setCanceled(true);
                                        }else{
                                            personalRP.confirmCredit(clientRP);
                                        }
                                        choice = "0";
                                    }break;
                                }
                            }break;
                            case "3":
                                loginClient = clientRP.singUpUser(id);
                                bankRP.addClientToBank(loginClient, id);
                                break;
                            case "4":
                                for (int a : bankRP.findBankById(id).getClientsList()) {
                                    clientRP.findClientById(a).clientInfo();
                                }break;
                            case "5": isCanceled = true; break;
                        }
                        if (isCanceled){
                            break;
                        }
                    }break;
                }
                default:
                    System.out.println("Enter correct symbol!");
            }
        }
    }
}