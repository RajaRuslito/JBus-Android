package com.RajaYonandroRuslitoJBusAF.jbus_android.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Represents a payment entity, extending the Invoice class.
 * Stores information about the payment, including bus ID, departure date, and booked bus seats.
 */
public class Payment extends Invoice {
    private int busId;
    public Timestamp departureDate;
    public List<String> busSeats;

    /**
     * Constructor to initialize a Payment object with the provided details.
     *
     * @param buyerId       ID of the buyer associated with the payment.
     * @param renterId      ID of the renter associated with the payment.
     * @param busId         ID of the bus associated with the payment.
     * @param busSeats      List of booked bus seats.
     * @param departureDate Timestamp representing the departure date.
     */
    public Payment(int buyerId, int renterId, int busId, List<String> busSeats, Timestamp departureDate) {
        super(buyerId, renterId);
        this.busId = busId;
        this.departureDate = departureDate;
        this.busSeats = busSeats;
    }

    /**
     * Constructor to initialize a Payment object with the provided buyer, renter, bus ID, bus seats, and departure date.
     *
     * @param buyer         Buyer associated with the payment.
     * @param renter        Renter associated with the payment.
     * @param busId         ID of the bus associated with the payment.
     * @param busSeats      List of booked bus seats.
     * @param departureDate Timestamp representing the departure date.
     */
    public Payment(Account buyer, Renter renter, int busId, List<String> busSeats, Timestamp departureDate) {
        super(buyer, renter);
        this.busId = busId;
        this.departureDate = departureDate;
        this.busSeats = busSeats;
    }

    /**
     * Gets the ID of the bus associated with the payment.
     *
     * @return Bus ID.
     */
    public int getBusId() {
        return this.busId;
    }

    /**
     * Gets a formatted string with details about the payment's departure information.
     *
     * @return Formatted string with departure information.
     */
    public String getDepartureInfo() {
        SimpleDateFormat format = new SimpleDateFormat("MMMM d, yyyy HH:mm:ss");
        String date = format.format(this.departureDate.getTime());
        String printLine = "Payment Details = " + " | ID : " + String.valueOf(id) + " | Bus ID : " + String.valueOf(busId) + " | Departure Date : " + date + " | Bus Seat : " + this.busSeats.toString() + " |";
        return printLine;
    }

    /**
     * Checks if a seat is available on a given departure schedule for a specific bus.
     *
     * @param /departureSchedule Timestamp of the departure schedule.
     * @param /seat              Seat to check for availability.
     * @param /bus               Bus to check for seat availability.
     * @return True if the seat is available, false otherwise.
     */
    public String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("MMMM d, yyyy HH:mm:ss");

        return format.format(this.departureDate.getTime());
    }

    /**
     * Finds the available schedule for a specific seat on a given departure timestamp and bus.
     *
     * @param /timestamp Timestamp of the departure schedule.
     * @param /seat      Seat to find availability for.
     * @param /bus       Bus to check for schedule availability.
     * @return Schedule if available, null otherwise.
     */
    public static boolean isAvailable(Timestamp departureSchedule, String seat, Bus bus) {

        for (Schedule sched : bus.schedules) {
            if (sched.departureSchedule.equals(departureSchedule)) {
                for (String seatName : sched.seatAvailability.keySet()) {
                    if (seatName.equals(seat)) {
                        return sched.seatAvailability.get(seat);
                    }
                }
            }
        }
        return false;
    }

    /**
     * Finds the available schedule for a list of seats on a given departure timestamp and bus.
     *
     * @param /timestamp Timestamp of the departure schedule.
     * @param /list      List of seats to find availability for.
     * @param /bus       Bus to check for schedule availability.
     * @return Schedule if available, null otherwise.
     */
    public static Schedule availableSchedule(Timestamp timestamp, String seat, Bus bus) {
        for (Schedule sched : bus.schedules) {
            if (sched.departureSchedule.getTime() == timestamp.getTime()) {
                for (String s : sched.seatAvailability.keySet()) {
                    if (s.equals(seat)) {
                        return sched;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Finds the available schedule for a list of seats on a given departure timestamp and bus.
     *
     * @param /timestamp Timestamp of the departure schedule.
     * @param /list      List of seats to find availability for.
     * @param /bus       Bus to check for schedule availability.
     * @return Schedule if available, null otherwise.
     */
    public static Schedule availableSchedule(Timestamp timestamp, List<String> list, Bus bus) {
        for (Schedule sched : bus.schedules) {
            if (sched.departureSchedule.getTime() == timestamp.getTime()) {
                if (sched.seatAvailability.keySet().containsAll(list)) {
                    return sched;
                }
            }
        }
        return null;
    }

}
