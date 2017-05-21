package com.chio.service;

import com.chio.data.dao.FlightRequestDao;
import com.chio.model.FlightRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightRequestService {

    @Autowired
    public FlightRequestDao flightRequestDao;

    public FlightRequest create(FlightRequest flightRequest) {
        return flightRequestDao.create(flightRequest);
    }

    public FlightRequest findByUserIdAndTripId(String userId, String tripId) {
        return flightRequestDao.findByUserIdAndTripId(userId, tripId);
    }
}
