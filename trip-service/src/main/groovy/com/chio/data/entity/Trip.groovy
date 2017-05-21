package com.chio.data.entity

import groovy.transform.Canonical;

import javax.persistence.*;

@Entity
@Canonical
public class Trip {

    @Id
    @GeneratedValue
    long id;

    long userId;
    String origin;
    String destination;
    String date;

}
