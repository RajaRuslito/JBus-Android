package com.RajaYonandroRuslitoJBusAF.jbus_android.model;

import java.util.ArrayList;

/**
 * The Account class represents a user account with basic information.
 */
public class Account extends Serializable {
    public String name;
    public String email;
    public String password;
    public double balance;
    public Renter company;

    /**
     * Constructs a new Account object with the specified parameters.
     *
     * @param name     The name of the account holder.
     * @param email    The email associated with the account.
     * @param password The password for the account.
     * @param balance  The balance in the account.
     */
    public Account(String name, String email, String password, double balance){
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = balance;
    }

}
