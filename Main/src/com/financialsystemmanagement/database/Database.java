package com.financialsystemmanagement.database;

import com.financialsystemmanagement.clientbuilder.ClientBuilder;
import com.financialsystemmanagement.enterprices.Banks;
import com.financialsystemmanagement.users.BankClient;
import com.financialsystemmanagement.users.BankManager;
import com.financialsystemmanagement.users.BankOperator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Database{
    private String clientRepositoryPath;
    private String bankRepositoryPath;
    private String personalRepositoryPath;
    private String changesRepositoryPath;

    public Database(String bankRepositoryPath, String clientRepositoryPath, String personalRepositoryPath,
                    String changesRepositoryPath){
        this.bankRepositoryPath = bankRepositoryPath;
        this.personalRepositoryPath = personalRepositoryPath;
        this.clientRepositoryPath = clientRepositoryPath;
        this.changesRepositoryPath = changesRepositoryPath;
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

    public void saveToChanges(List<String> changesLines) throws IOException{
        Files.write(Paths.get(changesRepositoryPath), changesLines);
    }

    public List<String> loadFromBank() throws IOException {
        return Files.readAllLines(Paths.get(bankRepositoryPath));
    }

    public List<String> loadFromClients() throws IOException {
        return Files.readAllLines(Paths.get(clientRepositoryPath));
    }

    public List<String> loadFromPersonal() throws IOException {
        return Files.readAllLines(Paths.get(personalRepositoryPath));
    }

    public List<String> loadFromChanges() throws IOException {
        return Files.readAllLines(Paths.get(changesRepositoryPath));
    }

    public Banks deserializeBank(String lines){
        String[] buf = lines.split("/");
        String[] clientBuf = buf[6].split("%");
        List<Integer> list = new ArrayList<Integer>();
        for (String a: clientBuf) {
            list.add(Integer.parseInt(a));
        }
        return new Banks(buf[1], buf[2], buf[3], buf[4],
                Integer.parseInt(buf[5]), Integer.parseInt(buf[0]), list);
    }

    public BankClient deserializeClient(String lines){
        ClientBuilder builder = new ClientBuilder();
        String[] buf = lines.split("/");
        String[] bankBuf = buf[8].split("");
        List<Integer> list = new ArrayList<>();
        for (String s: bankBuf) {
            list.add(Integer.parseInt(s));
        }
        return builder.setUserId(Integer.parseInt(buf[0])).setPersonalName(buf[1]).setPassword(buf[2]).
                setPassportNumber(buf[3]).setIdentificationNumber(buf[4]).setPhoneNumber(buf[5]).setEmail(buf[6]).
                setMoneyCount(Integer.parseInt(buf[7])).setBankList(list).
                setBlocked(Boolean.parseBoolean(buf[9])).getResult();
    }

    public BankOperator deserializeOperator(String lines){
        String[] personal = lines.split("/");
        if(personal[1].equals("Operator")){
            return new BankOperator(personal[1],personal[2],Integer.parseInt(personal[0]),
                    Boolean.parseBoolean(personal[3]));
        }
        return null;
    }

    public BankManager deserializeManager(String lines){
        String[] personal = lines.split("/");
        if(personal[1].equals("Manager")){
            return new BankManager(personal[1],personal[2],Integer.parseInt(personal[0]),
                    Boolean.parseBoolean(personal[3]));
        }
        return null;
    }

    public String serializePersonal(BankManager bankManager){
        return bankManager.getUserId() + "/" + bankManager.getPersonalName() + "/" + bankManager.getPassword()
                + "/" + bankManager.getIsCancel();
    }

    public String serializePersonal(BankOperator bankOperator){
        return bankOperator.getUserId() + "/" + bankOperator.getPersonalName() + "/" + bankOperator.getPassword()
                + "/" + bankOperator.getIsCancel();
    }

    public String serializeClient(BankClient client){
        String bankList = "";
        for (int s: client.getBanksList()) {
            bankList += s + "";
        }
        return client.getUserId() + "/" + client.getPersonalName() + "/" + client.getPassword() + "/" +
                client.getPassportNumber() + "/" + client.getIdentificationNumber() + "/" +
                client.getPhoneNumber() + "/" + client.getEmail()+ "/" + client.getMoneyCount() + "/" +
                bankList + "/" + client.getIsBlocked();
    }

    public String serializeBank(Banks banks){
        String clientList = "";
        for (int s: banks.getClientsList()) {
            clientList += s + "%";
        }
        return banks.getBankId() + "/" + banks.getEnterpriseType() + "/" + banks.getEnterpriseName() + "/" +
                banks.getBikOfBank() + "/" + banks.getAddress() + "/" + banks.getUNP() + "/" + clientList;
    }

    public String serializeTransfer(BankClient client1, BankClient client2, int sum, int bankId){
        return client1.getUserId() + "/" + client2.getUserId() + "/" + sum + "/" + bankId;
    }

}
