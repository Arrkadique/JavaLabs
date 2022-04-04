package com.financialsystemmanagement.repositories;

import com.financialsystemmanagement.database.Database;
import com.financialsystemmanagement.interfaces.Client;
import com.financialsystemmanagement.interfaces.Personal;
import com.financialsystemmanagement.operations.Credit;
import com.financialsystemmanagement.users.BankClient;
import com.financialsystemmanagement.users.BankManager;
import com.financialsystemmanagement.users.BankOperator;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static com.financialsystemmanagement.main.UI.isInteger;

public class PersonalRepository implements Personal {
    private Database db;

    public PersonalRepository(Database db){
        this.db = db;
    }

    public void showTransfers(List<String> lines, Client client) throws IOException {
        int a = 0;
        for (String s: lines) {
            if (!s.equals("")) {
                String[] buf = s.split("/");
                System.out.println(a++ + ". " + client.findClientById(Integer.parseInt(buf[0])).getPersonalName() +
                        " transfered to " + client.findClientById(Integer.parseInt(buf[1])).getPersonalName()
                        + " -> " + buf[2]);
            }
        }
    }

    public BankManager findManagerByBankId(int bankId) throws IOException{
        for (String s: db.loadFromPersonal()) {
            if(db.deserializeManager(s).getUserId() == bankId){
                return db.deserializeManager(s);
            }
        }
        return null;
    }

    public BankOperator findOperatorByBankId(int bankId) throws IOException{
        for (String s: db.loadFromPersonal()) {
            if(db.deserializeOperator(s).getUserId() == bankId){
                return db.deserializeOperator(s);
            }
        }
        return null;
    }

    public void confirmCredit(Client client) throws IOException{
        Scanner in = new Scanner(System.in);
        String choice;
        List<String> fromCredits = db.loadFromCredits();
        List<String> fromClients = db.loadFromClients();
        int a = 0;
        for (String s: fromCredits) {
            if(!db.deserializeCredit(s).isConfirmed()){
                System.out.println(a++ + ". " + client.findClientById(db.deserializeCredit(s).
                        getUserId()).getPersonalName() + " ->" +
                        db.deserializeCredit(s).getCreditInfo());
            }
        }
        System.out.println("What credit would you like to confirm?");
        while(true){
            choice = in.nextLine();
            if(isInteger(choice)){
                break;
            } else System.out.println("Try again");
        }
        Credit cr = db.deserializeCredit(fromCredits.get(Integer.parseInt(choice)));
        cr.setConfirmed(true);
        BankClient bc = client.findClientById(db.deserializeCredit(fromCredits.get(Integer.parseInt(choice)))
                        .getUserId());
        bc.addMoney(db.deserializeCredit(fromCredits.get(Integer.parseInt(choice))).getSum());
        for (String s: fromClients) {
            if(bc.getUserId() == db.deserializeClient(s).getUserId()){
                fromClients.set(fromClients.indexOf(s),db.serializeClient(bc));
            }
        }
        fromCredits.set(Integer.parseInt(choice), db.serializeCredit(cr));
        db.saveToClients(fromClients);
        db.saveToCredits(fromCredits);
    }

    public void cancelManagerAction(int bankId, Client client) throws IOException{
        if(!findManagerByBankId(bankId).getIsCancel()) {
            List<String> fromChanges = db.loadFromChanges();
            Scanner in = new Scanner(System.in);
            String str;
            showTransfers(fromChanges, client);
            System.out.println("What action would you like to cancel?");
            while (true) {
                str = in.nextLine();
                if (isInteger(str)) {
                    String[] buf = fromChanges.get(Integer.parseInt(str)).split("/");
                    client.autoTransfer(client.findClientById(Integer.parseInt(buf[1])),
                            client.findClientById(Integer.parseInt(buf[0])),
                            Integer.parseInt(buf[2]));
                    fromChanges.set(Integer.parseInt(str), "");
                    db.saveToChanges(fromChanges);
                    break;
                }
            }
        } else{
            System.out.println("You can't do this!");
        }
    }

    public void cancelOperatorAction(int bankId, Client client) throws IOException{
        if(!findOperatorByBankId(bankId).getIsCancel()) {
            List<String> fromChanges = db.loadFromChanges();
            Scanner in = new Scanner(System.in);
            String str;
            showTransfers(fromChanges, client);
            System.out.println("What action would you like to cancel?");
            while (true) {
                str = in.nextLine();
                if (isInteger(str)) {
                    String[] buf = fromChanges.get(Integer.parseInt(str)).split("/");
                    client.autoTransfer(client.findClientById(Integer.parseInt(buf[1])),
                            client.findClientById(Integer.parseInt(buf[0])),
                            Integer.parseInt(buf[2]));
                    fromChanges.set(Integer.parseInt(str), "");
                    db.saveToChanges(fromChanges);
                    break;
                }
            }
        } else {
            System.out.println("You can't do this!");
        }
    }

    public void cancelAllActions(int bankId, Client client) throws IOException{
        List<String> fromChanges = db.loadFromChanges();
        for (String s: fromChanges) {
            if (!s.equals("")) {
                String[] buf = s.split("/");
                if (Integer.parseInt(buf[3]) == bankId) {
                    client.autoTransfer(client.findClientById(Integer.parseInt(buf[1])),
                            client.findClientById(Integer.parseInt(buf[0])),
                            Integer.parseInt(buf[2]));
                    fromChanges.set(fromChanges.indexOf(s), "");
                }
            }
        }
        db.saveToChanges(fromChanges);
    }

}
