package com.financialsystemmanagement.repositories;

import com.financialsystemmanagement.database.Database;
import com.financialsystemmanagement.interfaces.Client;
import com.financialsystemmanagement.interfaces.Operations;
import com.financialsystemmanagement.operations.Credit;
import com.financialsystemmanagement.operations.Installment;
import com.financialsystemmanagement.users.BankClient;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static com.financialsystemmanagement.main.UI.isInteger;

public class OperationsRepository implements Operations {
    Database db;

    public OperationsRepository(Database db) {
        this.db = db;
    }

    public void showInstallments(Client client) throws IOException{
        for (String s: db.loadFromInstallments()) {
            System.out.println(client.findClientById(db.deserializeInstallment(s).getUserId()).getPersonalName()
                    + " ->" + db.deserializeInstallment(s).getInstallmentInfo());
        }
    }

    public void showCredits(Client client) throws IOException{
        for (String s: db.loadFromCredits()) {
            System.out.println(client.findClientById(db.deserializeCredit(s).getUserId()).getPersonalName()
                    + " ->" + db.deserializeCredit(s).getCreditInfo());
        }
    }

    public void takeInstallment(BankClient bankClient) throws IOException {
        List<String> fromInstallment = db.loadFromInstallments();
        Scanner in = new Scanner(System.in);
        System.out.println("What sum do you need?");
        String sum, choice;
        while (true){
            sum = in.nextLine();
            if(isInteger(sum)){
                break;
            }else {
                System.out.println("Try again!");
            }
        }
        System.out.println("Choose a term:\n" +
                "1. 3 month\n" +
                "2. 6 month\n" +
                "3. 12 month\n" +
                "4. 24 month");
        while (true){
            choice = in.nextLine();
            if(choice.equals("1") || choice.equals("2") || choice.equals("3")
                    || choice.equals("4")){
                if(choice.equals("1")) choice = "3";
                if(choice.equals("2")) choice = "6";
                if(choice.equals("3")) choice = "12";
                if(choice.equals("4")) choice = "24";
                break;
            }else {
                System.out.println("Try again!");
            }
        }
        fromInstallment.add(db.serializeInstallment(new Installment(bankClient.getUserId(), Integer.parseInt(sum),
                Integer.parseInt(choice))));
        db.saveToInstallments(fromInstallment);
    }

    public void takeCredit(BankClient bankClient) throws IOException{
        List<String> fromCredits = db.loadFromCredits();
        Scanner in = new Scanner(System.in);
        System.out.println("What sum do you need?");
        String sum, choice;
        while (true){
            sum = in.nextLine();
            if(isInteger(sum)){
                break;
            }else {
                System.out.println("Try again!");
            }
        }
        System.out.println("""
                Choose a term:
                1. 3 month
                2. 6 month
                3. 12 month
                4. 24 month""");
        while (true){
            choice = in.nextLine();
            if(choice.equals("1") || choice.equals("2") || choice.equals("3")
                    || choice.equals("4")){
                if(choice.equals("1")) choice = "3";
                if(choice.equals("2")) choice = "6";
                if(choice.equals("3")) choice = "12";
                if(choice.equals("4")) choice = "24";
                break;
            }else {
                System.out.println("Try again!");
            }
            fromCredits.add(db.serializeCredit(new Credit(bankClient.getUserId(),Integer.parseInt(sum),
                    Integer.parseInt(choice), 25, false)));
            db.saveToCredits(fromCredits);
        }
    }

}
