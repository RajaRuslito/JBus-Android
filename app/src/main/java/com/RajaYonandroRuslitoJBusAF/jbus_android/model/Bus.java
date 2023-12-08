package com.RajaYonandroRuslitoJBusAF.jbus_android.model;

import androidx.annotation.NonNull;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

    /*public Bus(String name, List<Facility> facilities, Price price, int capacity, BusType busType, City city, Station departure, Station arrival, int accountId){
        super();
        this.name = name;
        this.facilities = facilities;
        this.price = price;
        this.capacity = capacity;
        this.departure = departure;
        this.arrival = arrival;
        this.busType = busType;
        //this.city = city;
        this.schedules = new ArrayList<>();
        this.accountId = id;
    }*/
    /*public void addSchedule(Timestamp calendar){
        Schedule schedule = new Schedule(calendar, capacity);
        schedules.add(schedule);
    }*//*
    public String toString(){
        return " ID : " + id + "\n String objek : " + name + "\n Facility : " + facilities + "\n Price : " + price + "\n Capacity : " + capacity + "\n Bus Type : " + busType + "\n City : " *//*+ city*//* + "\n\n Departure = " + departure + "\n Arrival = " + arrival;
    }
    public boolean read(String obj){
        return false;
    }
    public Object write(){
        return null;
    }*/

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

    @NonNull
    @Override
    public String toString() {
        /*Bus bus = new Bus();
        return bus.name;*/
        return schedules.toString();
    }
}

