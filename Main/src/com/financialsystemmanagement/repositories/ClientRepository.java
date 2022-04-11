package com.financialsystemmanagement.repositories;

import com.financialsystemmanagement.clientbuilder.ClientBuilder;
import com.financialsystemmanagement.database.Database;
import com.financialsystemmanagement.enterprices.Banks;
import com.financialsystemmanagement.interfaces.Client;
import com.financialsystemmanagement.users.BankClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.financialsystemmanagement.main.UI.isInteger;

public class ClientRepository implements Client {
    private final Database db;
    private final ClientBuilder builder;

    public ClientRepository(Database db){
        this.db = db;
        builder = new ClientBuilder();
    }

    public BankClient findClientById(int id)throws IOException{
        List<String> lines = db.loadFromClients();
        for (String s: lines) {
            BankClient a = db.deserializeClient(s);
            if(a.getUserId() == id){
                return a;
            }
        }
        return null;
    }

    public List<BankClient> findClientByName(String name) throws IOException{
        List<String> lines = db.loadFromClients();
        List<BankClient> bc = new ArrayList<>();
        for (String s: lines) {;
            if(db.deserializeClient(s).getPersonalName().equals(name)){
                bc.add(db.deserializeClient(s));
            }
        }
        return bc;
    }

    public boolean isInBank(BankClient bankClient, Banks banks){
        return bankClient.getBankId() == banks.getBankId();
    }

    public boolean isInBank(List<BankClient> bankClient, Banks banks){
        for (BankClient b: bankClient) {
            if(b.getBankId() == banks.getBankId()){
                return true;
            }
        }
        return false;
    }

    public BankClient addBankAccount(BankClient bankClient) throws IOException{
        List<String> fromClients = db.loadFromClients();
        List<String> fromLogs = db.loadFromLogs();
        Scanner in = new Scanner(System.in);
        String id;
        System.out.println("Enter id: ");
        do{
            id = in.nextLine();
            if(!isRepeatId(Integer.parseInt(id))){
                break;
            }
            System.out.println("This id isn't available!");
        } while (true);
        BankClient b = builder.setUserId(Integer.parseInt(id)).
                setEmail(bankClient.getEmail()).
                setPersonalName(bankClient.getPersonalName()).
                setIdentificationNumber(bankClient.getIdentificationNumber()).
                setBlocked(bankClient.getIsBlocked()).setPassword(bankClient.getPassword()).
                setPassportNumber(bankClient.getPassportNumber()).
                setBankId(bankClient.getBankId()).setMoneyCount(0).
                setPhoneNumber(bankClient.getPhoneNumber()).getResult();
         fromClients.add(db.serializeClient(b));
         fromLogs.add("New account added!");
         db.saveToClients(fromClients);
         db.saveToLogs(fromLogs);
         return b;
    }

    public BankClient singUpUser(int bankId) throws IOException{
        List<String> fromLogs = db.loadFromLogs();
        List<String> fromClients = db.loadFromClients();
        Scanner in = new Scanner(System.in);
        String name, id;
        System.out.println("Enter id: ");
        do{
            id = in.nextLine();
            if(!isRepeatId(Integer.parseInt(id))){
                break;
            }
            System.out.println("This id isn't available!");
        } while (true);
        System.out.println("\nEnter name: ");
        do{
            name = in.nextLine();
            if(!isRepeatName(name)){
                break;
            }
            System.out.println("This name isn't available!");
        } while (true);
        builder.setUserId(Integer.parseInt(id));
        builder.setPersonalName(name);
        System.out.println("Enter password: ");
        builder.setPassword(in.nextLine());
        System.out.println("Enter email: ");
        builder.setEmail(in.nextLine());
        System.out.println("Enter identification number: ");
        builder.setIdentificationNumber(in.nextLine());
        System.out.println("Enter passport number: ");
        builder.setPassportNumber(in.nextLine());
        System.out.println("Enter phone: ");
        builder.setPhoneNumber(in.nextLine());
        builder.setBankId(bankId);
        fromLogs.add("New client added!");
        fromClients.add(db.serializeClient(builder.getResult()));
        db.saveToClients(fromClients);
        db.saveToLogs(fromLogs);
        return builder.getResult();
    }

    public boolean isRepeatId(int id) throws IOException {
        List<String> lines = db.loadFromClients();
        for (String s: lines) {
            if(db.deserializeClient(s).getUserId() == id){
                return true;
            }
        }
        return false;
    }

    public boolean isRepeatName(String name) throws IOException {
        List<String> lines = db.loadFromClients();
        for (String s: lines) {
            if(db.deserializeClient(s).getPersonalName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public void makeTransfer(BankClient bankClient1, int bankId) throws IOException{
        if (!bankClient1.getIsBlocked()) {
            Scanner in = new Scanner(System.in);
            List<String> fromLogs = db.loadFromLogs();
            List<String> fromChanges = db.loadFromChanges();
            List<String> fromClients = db.loadFromClients();
            String id;
            String sum;
            while (true) {
                id = in.nextLine();
                if (isInteger(id)) {
                    if (Integer.parseInt(id) != bankClient1.getUserId())
                        break;
                }
                System.out.println("You can't transfer money yourself!");
            }
            BankClient bankClient2 = findClientById(Integer.parseInt(id));
            while (true) {
                sum = in.nextLine();
                if (isInteger(sum)) {
                    if (Integer.parseInt(sum) < bankClient1.getMoneyCount())
                        break;
                }
                System.out.println("You doesn't have this money!");
            }
            bankClient1.removeMoney(Integer.parseInt(sum));
            bankClient2.addMoney(Integer.parseInt(sum));
            for (String s : fromClients) {
                if (bankClient1.getUserId() == db.deserializeClient(s).getUserId()) {
                    fromClients.set(fromClients.indexOf(s), db.serializeClient(bankClient1));
                }
                if (bankClient2.getUserId() == db.deserializeClient(s).getUserId()) {
                    fromClients.set(fromClients.indexOf(s), db.serializeClient(bankClient2));
                }
            }
            fromChanges.add(db.serializeTransfer(bankClient1, bankClient2, Integer.parseInt(sum), bankId));
            fromLogs.add("Transfer was made!");
            db.saveToLogs(fromLogs);
            db.saveToChanges(fromChanges);
            db.saveToClients(fromClients);
        } else {
            System.out.println("Your account is blocked!");
        }
    }

    public void autoTransfer(BankClient bankClient1, BankClient bankClient2, int sum) throws IOException{
        if (!bankClient1.getIsBlocked()) {
            List<String> fromLogs = db.loadFromLogs();
            List<String> fromClients = db.loadFromClients();
            bankClient1.removeMoney(sum);
            bankClient2.addMoney(sum);
            for (String s : fromClients) {
                if (bankClient1.getUserId() == db.deserializeClient(s).getUserId()) {
                    fromClients.set(fromClients.indexOf(s), db.serializeClient(bankClient1));
                }
                if (bankClient2.getUserId() == db.deserializeClient(s).getUserId()) {
                    fromClients.set(fromClients.indexOf(s), db.serializeClient(bankClient2));
                }
            }
            fromLogs.add("Action was canceled!");
            db.saveToLogs(fromLogs);
            db.saveToClients(fromClients);
        } else {
            System.out.println("Your account is blocked!");
        }
    }

    public void moneyWithdrawal(BankClient bankClient) throws IOException{
        if(!bankClient.getIsBlocked()) {
            Scanner in = new Scanner(System.in);
            List<String> fromLogs = db.loadFromLogs();
            List<String> fromClients = db.loadFromClients();
            String sum;

            System.out.println("What sum do you need?");
            while (true) {
                sum = in.nextLine();
                if (isInteger(sum)) {
                    if (Integer.parseInt(sum) < bankClient.getMoneyCount())
                        break;
                }
                System.out.println("You doesn't have this money!");
            }
            bankClient.removeMoney(Integer.parseInt(sum));
            for (String s : fromClients) {
                if (bankClient.getUserId() == db.deserializeClient(s).getUserId()) {
                    fromClients.set(fromClients.indexOf(s), db.serializeClient(bankClient));
                }
            }
            fromLogs.add("Withdrawal was did!");
            db.saveToLogs(fromLogs);
            db.saveToClients(fromClients);
        } else {
            System.out.println("Your account is blocked!");
        }
    }
}
