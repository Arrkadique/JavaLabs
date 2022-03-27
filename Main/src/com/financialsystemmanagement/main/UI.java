package com.financialsystemmanagement.main;

import com.financialsystemmanagement.database.Database;
import com.financialsystemmanagement.enterprices.Bank;
import com.financialsystemmanagement.interfaces.IBank;
import com.financialsystemmanagement.interfaces.IClient;
import com.financialsystemmanagement.users.BankClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UI {

    public static boolean isInteger(String s){
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to Integer.");
            return false;
        }
    }

    public static void mainMenu(){
        System.out.println("---------------\n" +
                "Choose an action:\n" +
                "1. Show banks\n" +
                "2. Show all clients\n" +
                "3. Show all companies\n" +
                "4. Choose a bank");
    }

    public static void showAllBanks(List<String> lines, Database db){
        Bank b;
        for (String s: lines) {
            b = db.deserializeBank(s);
            b.showInfo();
        }
    }

    public static void showAllClients(List<String> lines, Database db){
        BankClient b;
        for (String s: lines) {
            b = db.deserializeClient(s);
            b.clientInfo();
        }
    }

    public static void bankStartLogInMenu(){
        System.out.println("What would you like to do?\n" +
                "1. Log in\n" +
                "2. Add new client\n" +
                "3. Show all clients");
    }

    public static List<Integer> getBankClientsList(Bank bank,List<String> clients, Database db){
        List<Integer> id = new ArrayList<>();
        for (int a: bank.getClientsList()) {
            for (String s: clients) {
                for (int b: db.deserializeClient(s).getBanksList()) {
                    if(b == a){
                        id.add(db.deserializeClient(s).getUserId());
                    }
                }
            }
        }
        return id;
    }

    public static BankClient bankLoginMenu(List<String> clients, IClient bc, Bank b){
        System.out.println("---------------\n" +
                "Welcome to " + b.getEnterpriseName());
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String name = in.nextLine();
        if(bc.findClientByName(clients, name) != null){
            System.out.println("Enter password: " + bc.findClientByName(clients, name).getPassword());
            if(bc.findClientByName(clients, name).getPassword().equals(in.nextLine())){
                return bc.findClientByName(clients, name);
            }else {
                System.out.println("\nWrong Password!");
            }
        } else {
            System.out.println("\nThere aren't client with this name!");
        }
        return null;
    }

    public static int choosingBank(List<String> b, IBank br, Database db){
        Scanner in = new Scanner(System.in);
        String id;
        for (String s : b) {
            db.deserializeBank(s).showInfo();
        }
        System.out.println("Enter bank ID: ");
        while (true) {
            id = in.nextLine();
            if (!isInteger(id)) {
            } else if(br.findBankById(b, Integer.parseInt(id)) != null) {
                br.findBankById(b, Integer.parseInt(id)).showInfo();
                System.out.println("---------------");
                break;
            } else {
                System.out.println("There aren't bank with this id!");
            }
        }
        return Integer.parseInt(id);
    }

}
