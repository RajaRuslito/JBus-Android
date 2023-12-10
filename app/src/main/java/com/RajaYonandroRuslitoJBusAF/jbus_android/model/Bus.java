package com.RajaYonandroRuslitoJBusAF.jbus_android.model;

import androidx.annotation.NonNull;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * The Bus class represents a bus with various details such as capacity, facilities, schedule, etc.
 */
public class Bus extends Serializable
{
    public int capacity;
    public List<Facility> facilities;
    public String name;
    public Price price;
    public Station departure;
    public Station arrival;
    public BusType busType;
    //public City city;
    public List<Schedule> schedules;
    public int accountId;

    /**
     * Generates a sample list of buses with specified size for testing purposes.
     *
     * @param size The size of the sample bus list.
     * @return A list of sample buses.
     */
    public static List<Bus> sampleBusList(int size) {
        List<Bus> busList = new ArrayList<Bus>();

        for (int i = 1; i <= size; i++) {
            Bus bus = new Bus();
            bus.name = "Bus " + i;
            bus.departure = new Station();
            bus.departure.city = City.JAKARTA;
            bus.arrival = new Station();
            bus.arrival.city = City.BANDUNG;
            bus.price = new Price();
            bus.price.price = 15000000;
            bus.busType = BusType.DOUBLE_DECKER;
            bus.facilities = new ArrayList<>();
            bus.facilities.add(Facility.AC);
            bus.facilities.add(Facility.WIFI);
            bus.capacity = 20;
            busList.add(bus);
        }
        return busList;
    }

    /**
     * Returns a string representation of the bus.
     *
     * @return A string containing bus details.
     */
    @NonNull
    @Override
    public String toString() {
        /*Bus bus = new Bus();
        return bus.name;*/
        return schedules.toString();
    }
}

