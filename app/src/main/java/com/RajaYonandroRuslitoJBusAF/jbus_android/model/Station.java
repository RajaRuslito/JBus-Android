package com.RajaYonandroRuslitoJBusAF.jbus_android.model;

import androidx.annotation.NonNull;

/**
 * The Station class represents a bus station with a unique identifier, name, city, and address.
 */
public class Station extends Serializable {
    public String stationName;
    public City city;
    public String address;

    /**
     * Gets the name of the bus station.
     *
     * @return The name of the bus station.
     */
    public String toString() {
        return stationName;
    }
}