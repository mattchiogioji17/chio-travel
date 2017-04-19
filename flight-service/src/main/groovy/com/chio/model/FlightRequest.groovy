package com.chio.model

import groovy.transform.Canonical

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
@Canonical
public class FlightRequest {

    @Id
    @GeneratedValue
    long id;

    String userId;
    String tripId;
    String origin;
    String destination;
    String date;
    int adultCount;
    int infantInLapCount;
    int childCount;
    int seniorCount;
    int solutions;

}

