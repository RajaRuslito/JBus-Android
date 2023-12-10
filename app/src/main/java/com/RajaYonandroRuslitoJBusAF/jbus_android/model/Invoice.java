package com.RajaYonandroRuslitoJBusAF.jbus_android.model;

import java.sql.Timestamp;

/**
 * The Invoice class represents an invoice for a bus transaction, including details like time, buyer ID, renter ID, rating, and status.
 */
public class Invoice extends Serializable
{
    public Timestamp time;
    public int buyerId;
    public int renterId;
    public BusRating rating;
    public PaymentStatus status;

    /**
     * Enumeration representing possible bus ratings.
     */
    public enum BusRating{
        NONE,
        NEUTRAL,
        GOOD,
        BAD;
    }

    /**
     * Enumeration representing possible payment statuses.
     */
    public enum PaymentStatus{
        FAILED,
        WAITING,
        SUCCESS;
    }

    /**
     * Protected constructor for creating an invoice with buyer and renter IDs.
     *
     * @param buyerId  The ID of the buyer.
     * @param renterId The ID of the renter.
     */
    protected Invoice(int buyerId, int renterId){
        super();
        this.buyerId = buyerId;
        this.renterId = renterId;
        this.time = new Timestamp(System.currentTimeMillis());
        this.rating = BusRating.NONE;
        this.status = PaymentStatus.WAITING;
    }

    /**
     * Constructor for creating an invoice with buyer and renter objects.
     *
     * @param buyer The buyer associated with the invoice.
     * @param renter The renter associated with the invoice.
     */
    public Invoice(Account buyer, Renter renter){
        super();
        this.buyerId = buyer.id;
        this.renterId = renter.id;
        this.time = new Timestamp(System.currentTimeMillis());
        this.rating = BusRating.NONE;
        this.status = PaymentStatus.WAITING;
    }

    /**
     * Returns a string representation of the invoice.
     *
     * @return A string containing invoice details.
     */
    public String toString(){
        return "\n ID : " + id + "\n Buyer ID : " + buyerId + "\n Renter ID : " + renterId + "\n Time : " + time.getTime() + "\n Rating : " + rating + "\n Status : " + status;
    }
}
