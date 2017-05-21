package com.chio.data.entity;

import com.chio.model.FlightRequest;

import javax.persistence.*;

@Entity
public class FlightDetails {

    @Id
    @GeneratedValue
    long id;
    long userId;
    long tripId;

    @OneToOne
    @JoinColumn(name = "id")
    FlightRequest flightRequest;


}
