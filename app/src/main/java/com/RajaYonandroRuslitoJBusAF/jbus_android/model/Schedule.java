package com.RajaYonandroRuslitoJBusAF.jbus_android.model;

import androidx.annotation.NonNull;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class Schedule {
    public Timestamp departureSchedule;
    public Map<String, Boolean> seatAvailability;

    @NonNull
    @Override
    public String toString() {
        return departureSchedule.toString();
    }

    /*@NonNull
    @Override
    public String toString() {
        int countOccupied = 0;
        for (boolean val : seatAvailability.values()) {
            if (!val) countOccupied++;
        }
        int totalSeat = seatAvailability.size();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss");
        return dateFormat.format(this.departureSchedule.getTime()) + "\t\t" +"[ "+countOccupied + "/" + totalSeat+" ]";
    }*/
}
