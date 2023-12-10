package com.RajaYonandroRuslitoJBusAF.jbus_android.request;

import android.widget.TextView;

import com.RajaYonandroRuslitoJBusAF.jbus_android.PaymentActivity;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Account;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.BaseResponse;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Bus;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.BusType;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.City;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Facility;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Payment;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Renter;
import com.RajaYonandroRuslitoJBusAF.jbus_android.model.Station;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
public interface BaseApiService {
    @GET("account/{id}")
    Call<Account> getAccountbyId (@Path("id") int id);

    @POST("account/register")
    Call<BaseResponse<Account>> register (
            @Query("name") String name,
            @Query("email") String email,
            @Query("password") String password
    );

    @POST("account/login")
    Call<BaseResponse<Account>> login (
            @Query("email") String email,
            @Query("password") String password
    );

    @POST("account/{id}/topUp")
    Call<BaseResponse<Double>> topUp(
            @Path("id") int id,
            @Query("amount") double amount
    );

    @POST("account/{id}/registerRenter")
    Call<BaseResponse<Renter>> registerRenter (
            @Path("id") int id,
            @Query("companyName") String companyName,
            @Query("phoneNumber") String phoneNumber,
            @Query("address") String address
    );

    @GET("station/getAll")
    Call<List<Station>> getAllStation();

    @POST("bus/create")
    Call<BaseResponse<Bus>> create (
            @Query("accountId") int accountId,
            @Query("name") String name,
            @Query("capacity") int capacity,
            @Query("facilities") List<Facility> facilities,
            @Query("busType") BusType busType,
            @Query("price") int price,
            @Query("stationDepartureId") int stationDepartureId,
            @Query("stationArrivalId") int stationArrivalId
    );

    @GET("bus/getMyBus")
    Call<List<Bus>> getMyBus(
            @Query("accountId") int accountId
    );

    @GET("bus/getBuses")
    Call<BaseResponse<List<Bus>>> getBuses(
           /* @Query("busId") int busId*/
    );

    @POST("bus/addSchedule")
    Call<BaseResponse<Bus>> addSchedule(
            @Query("busId") int busId,
            @Query("time") String time
    );

    @POST("payment/makeBooking")
    Call<BaseResponse<Payment>> makeBooking(
            @Query("buyerId") int buyerId,
            @Query("renterId") int renterId,
            @Query("busId") int busId,
            @Query("busSeats") List<String> busSeats,
            @Query("departureDate") String departureDate
    );

    @POST("payment/{id}/accept")
    Call<BaseResponse<Payment>> accept(
            @Path("id") int id,
            @Query("buyerId") int buyerId,
            @Query("busId") int busId
    );

    @POST("payment/{id}/cancel")
    Call<BaseResponse<Payment>> cancel(
            @Path("id") int id
    );
    @GET("payment/getMyPayment")
    Call<List<Payment>> getMyPayment(
            @Query("buyerId") int buyerId
    );

    @POST("station/create")
    Call<BaseResponse<Station>> createStation(
            @Query("stationName") String stationName,
            @Query("city") String city,
            @Query("address") String address
    );

}

