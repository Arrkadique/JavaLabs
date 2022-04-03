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
    private Database db;
    private ClientBuilder builder;

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

    public BankClient findClientByName(String name) throws IOException{
        List<String> lines = db.loadFromClients();
        for (String s: lines) {
            BankClient a = db.deserializeClient(s);
            if(a.getPersonalName().equals(name)){
                return a;
            }
        }
        return null;
    }

    public boolean isInBank(BankClient bankClient, Banks banks){
        for (int a: bankClient.getBanksList()) {
            if(a == banks.getBankId()){
                return true;
            }
        }
        return false;
    }

    public BankClient singUpUser(int bankId) throws IOException{
        Scanner in = new Scanner(System.in);
        String name, id;
        List<Integer> lst = new ArrayList<>();
        lst.add(bankId);
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
        builder.setBankList(lst);
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
            db.saveToChanges(fromChanges);
            db.saveToClients(fromClients);
        } else {
            System.out.println("Your account is blocked!");
        }
    }

    public void autoTransfer(BankClient bankClient1, BankClient bankClient2, int sum) throws IOException{
        if (!bankClient1.getIsBlocked()) {
            List<String> fromClients = db.loadFromClients();
            String id;
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
            db.saveToClients(fromClients);
        } else {
            System.out.println("Your account is blocked!");
        }
    }

    public void moneyWithdrawal(BankClient bankClient) throws IOException{
        if(!bankClient.getIsBlocked()) {
            Scanner in = new Scanner(System.in);
            List<String> fromChanges = db.loadFromChanges();
            List<String> fromClients = db.loadFromClients();
            String sum;
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
            db.saveToClients(fromClients);
        } else {
            System.out.println("Your account is blocked!");
        }
    }
}
