package com.financialsystemmanagement.enterprices;

public class Enterprise {
    private final String EnterpriseType;
    private final String EnterpriseName;
    private final String UNP;
    private final String Address;

    public Enterprise(String EnterpriseType, String EnterpriseName, String UNP, String Address){
        this.EnterpriseName = EnterpriseName;
        this.EnterpriseType = EnterpriseType;
        this.UNP = UNP;
        this.Address = Address;
    }

    public String getEnterpriseName() {
        return EnterpriseName;
    }

    //StringBuilder and StringBuffer
    public String EnterpriseInfo(){
        return EnterpriseType + " " + EnterpriseName + " " + UNP + " " + Address;
    }

}
