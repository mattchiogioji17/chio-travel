package com.chio.data.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Destination {

    @Id
    @GeneratedValue
    private long id;

    private String cityName;
    private String aiportCode;
}
