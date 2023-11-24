package com.RajaYonandroRuslitoJBusAF.jbus_android.model;

import java.util.ArrayList;

public class Account extends Serializable {
    public String name;
    public String email;
    public String password;
    public double balance;
    public Renter company;
    //public static ArrayList<Account> accountArrayList = new ArrayList<>();

    public Account(String name, String email, String password, double balance){
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = balance;
        //accountArrayList.add(this);

    }


    /*public void setBalance(double balance) {
        this.balance = balance;
    }*/
}
