package com.financialsystemmanagement.database;

import com.financialsystemmanagement.enterprices.Bank;
import com.financialsystemmanagement.users.BankClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Database{
    private String clientRepositoryPath;
    private String bankRepositoryPath;
    private String personalRepositoryPath;

    public Database(String bankRepositoryPath, String clientRepositoryPath, String personalRepositoryPath){
        this.bankRepositoryPath = bankRepositoryPath;
        this.personalRepositoryPath = personalRepositoryPath;
        this.clientRepositoryPath = clientRepositoryPath;
    }

    public void saveToBank(List<String> bankLines) throws IOException{
        Files.write(Paths.get(bankRepositoryPath), bankLines);
    }

    public void saveToClients(List<String> clientsLines) throws IOException{
        Files.write(Paths.get(clientRepositoryPath), clientsLines);
    }

    public void saveToPersonal(List<String> personalLines) throws IOException{
        Files.write(Paths.get(personalRepositoryPath), personalLines);
    }

    public List<String> loadFromBank() throws IOException {
        List<String> list = Files.readAllLines(Paths.get(bankRepositoryPath));
        return list;
    }

    public List<String> loadFromClients() throws IOException {
        List<String> list = Files.readAllLines(Paths.get(clientRepositoryPath));
        return list;
    }

    public List<String> loadFromPersonal() throws IOException {
        List<String> list = Files.readAllLines(Paths.get(personalRepositoryPath));
        return list;
    }

    public Bank deserializeBank(String lines){
        String[] buf = lines.split("/");
        String[] clientBuf = buf[6].split("");
        List<Integer> list = new ArrayList<Integer>();
        for (String a: clientBuf) {
            System.out.println(a);
            list.add(Integer.parseInt(a));
        }
        return new Bank(buf[1], buf[2], buf[3], buf[4],
                Integer.parseInt(buf[5]), Integer.parseInt(buf[0]), list);
    }

    public BankClient deserializeClient(String lines){
        String[] buf = lines.split("/");
        String[] bankBuf = buf[8].split("");
        List<Integer> list = new ArrayList<Integer>();
        for (String a: bankBuf) {
            list.add(Integer.parseInt(a));
        }
        return new BankClient(Integer.parseInt(buf[0]), buf[1], buf[2], buf[3], buf[4],
                buf[5], buf[6], Integer.parseInt(buf[7]), list);
    }

    public String serializeClient(BankClient client){
        return client.getUserId() + "/" + client.getPersonalName() + "/" + client.getPassword() + "/" +
                client.getPassportNumber() + "/" + client.getIdentificationNumber() + "/" +
                client.getPhoneNumber() + "/" + client.getEmail()+ "/" + client.getBanksList();
    }

    public String serializeBank(){
        return "";
    }
}
