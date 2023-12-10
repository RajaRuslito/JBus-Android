package com.RajaYonandroRuslitoJBusAF.jbus_android.model;

import androidx.annotation.NonNull;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The Schedule class represents the schedule of a bus, including the departure schedule and seat availability.
 */
public class Schedule {
    public Timestamp departureSchedule;
    public Map<String, Boolean> seatAvailability;

    /**
     * Constructor for creating a schedule with a specified departure schedule and number of seats.
     *
     * @param /departureSchedule The timestamp of the departure schedule.
     * @param /numberOfSeats     The number of seats available in the bus.
     */
    @NonNull
    @Override
    public String toString() {
        return departureSchedule.toString();
    }

    /**
     * Initializes seat availability based on the number of seats, marking all seats as initially available.
     *
     * @param numberOfSeats The number of seats available in the bus.
     */
    public Schedule(Timestamp departureSchedule, int numberOfSeats){
        this.departureSchedule = departureSchedule;
        initializeSeatAvailability(numberOfSeats);
    }

    /**
     * Checks if a specific seat is available.
     *
     * @param /seat The seat name to check for availability.
     * @return True if the seat is available, false otherwise.
     */
    private void initializeSeatAvailability(int numberOfSeats){
        seatAvailability = new LinkedHashMap<>();
        for (int seatNumber = 1; seatNumber <= numberOfSeats; seatNumber++) {
            String sn = seatNumber < 10 ? "0" + seatNumber : ""+ seatNumber;
            seatAvailability.put("AF" + sn, true);
        }
    }

    /**
     * Checks if a list of seats is available.
     *
     * @param /seat The list of seat names to check for availability.
     * @return True if all seats in the list are available, false otherwise.
     */
    public boolean isSeatAvailable(String seat){
        if(seatAvailability.containsKey(seat)){
            return seatAvailability.get(seat);
        }
        return false;
    }

    /**
     * Checks if a list of seats is available.
     *
     * @param /seat The list of seat names to check for availability.
     * @return True if all seats in the list are available, false otherwise.
     */
    public boolean isSeatAvailable(List<String> seat){
        for(int i = 0; i < seat.size(); i++){
            if(seatAvailability.containsKey(seat.get(i))){
                return seatAvailability.get(seat.get(i));
            }
        }
        return false;
    }

    /**
     * Books a specific seat, marking it as unavailable.
     *
     * @param /seat The seat name to be booked.
     */
    public void bookSeat(String seat){
        this.seatAvailability.put(seat, false);
    }

    /**
     * Books a list of seats, marking them as unavailable.
     *
     * @param /seats The list of seat names to be booked.
     */
    public void bookSeat(List<String> seat){
        for(String i : seat){
            seatAvailability.put(i, false);
        }
    }
}
