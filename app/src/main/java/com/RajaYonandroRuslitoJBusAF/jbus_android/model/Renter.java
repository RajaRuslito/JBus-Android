package com.RajaYonandroRuslitoJBusAF.jbus_android.model;

/**
 * Represents a renter or a company that can rent buses.
 */
public class Renter extends Serializable {
    public String phoneNumber;
    public String address;
    public String companyName;

    /**
     * Constructs a Renter object with the given company name.
     *
     * @param companyName The name of the company.
     */
    public Renter(String phoneNumber, String address, String companyName){
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.companyName = companyName;
    }

    public static class Algorithm {
    }
}
