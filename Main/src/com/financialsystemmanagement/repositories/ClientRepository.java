package com.financialsystemmanagement.repositories;

import com.financialsystemmanagement.database.Database;
import com.financialsystemmanagement.interfaces.IClient;
import com.financialsystemmanagement.users.BankClient;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static com.financialsystemmanagement.main.UI.isInteger;

public class ClientRepository implements IClient {
    private Database db;

    public ClientRepository(Database db){
        this.db = db;
    }

    public BankClient findClientById(List<String> lines, int id){
        for (String s: lines) {
            BankClient a = db.deserializeClient(s);
            if(a.getUserId() == id){
                return a;
            }
        }
        return null;
    }

    public BankClient findClientByName(List<String> lines, String name){
        for (String s: lines) {
            BankClient a = db.deserializeClient(s);
            if(a.getPersonalName().equals(name)){
                return a;
            }
        }
        return null;
    }

    public void addClient(BankClient client, List<String> lines){
        lines.add(db.serializeClient(client));
    }

    public BankClient singUpUser() throws IOException{
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
        return new BankClient(Integer.parseInt(id),name,"123","123","123",
                "123","123");
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

    public void makeTransfer(int id1, int id2) throws IOException{
        List<String> lines = db.loadFromClients();
    }
}
