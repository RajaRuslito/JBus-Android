package com.RajaYonandroRuslitoJBusAF.jbus_android.model;

public class Renter extends Serializable {
    public String phoneNumber;
    public String address;
    public String companyName;

    public Renter(String phoneNumber, String address, String companyName){
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.companyName = companyName;
    }

}
