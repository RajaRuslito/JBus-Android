package com.RajaYonandroRuslitoJBusAF.jbus_android.model;

import java.sql.Timestamp;

public class Invoice extends Serializable
{
    public Timestamp time;
    public int buyerId;
    public int renterId;
    public BusRating rating;
    public PaymentStatus status;

    public enum BusRating{
        NONE,
        NEUTRAL,
        GOOD,
        BAD;
    }
    public enum PaymentStatus{
        FAILED,
        WAITING,
        SUCCESS;
    }
    protected Invoice(int buyerId, int renterId){
        super();
        this.buyerId = buyerId;
        this.renterId = renterId;
        this.time = new Timestamp(System.currentTimeMillis());
        this.rating = BusRating.NONE;
        this.status = PaymentStatus.WAITING;
    }
    public Invoice(Account buyer, Renter renter){
        super();
        this.buyerId = buyer.id;
        this.renterId = renter.id;
        this.time = new Timestamp(System.currentTimeMillis());
        this.rating = BusRating.NONE;
        this.status = PaymentStatus.WAITING;
    }
    public String toString(){
        return "\n ID : " + id + "\n Buyer ID : " + buyerId + "\n Renter ID : " + renterId + "\n Time : " + time.getTime() + "\n Rating : " + rating + "\n Status : " + status;
    }
}
