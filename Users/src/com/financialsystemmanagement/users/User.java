package com.financialsystemmanagement.users;

abstract public class User{
    private int userId;
    private String personalName;
    private String password;

    public User(String personalName, String password, int userId){
        this.personalName = personalName;
        this.password = password;
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getPersonalName() {
        return personalName;
    }

}

