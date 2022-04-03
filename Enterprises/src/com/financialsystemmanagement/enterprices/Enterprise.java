package com.financialsystemmanagement.enterprices;

public class Enterprise {
    private String EnterpriseType;
    private String EnterpriseName;
    private String UNP;
    private String Address;

    public Enterprise(String EnterpriseType, String EnterpriseName, String UNP, String Address){
        this.EnterpriseName = EnterpriseName;
        this.EnterpriseType = EnterpriseType;
        this.UNP = UNP;
        this.Address = Address;
    }

    public Enterprise(){}

    public String getEnterpriseType() {
        return EnterpriseType;
    }

    public String getUNP() {
        return UNP;
    }

    public String getAddress() {
        return Address;
    }


    public String getEnterpriseName() {
        return EnterpriseName;
    }

    //StringBuilder and StringBuffer
    public String EnterpriseInfo(){
        return EnterpriseType + " " + EnterpriseName + " " + UNP + " " + Address;
    }

}
