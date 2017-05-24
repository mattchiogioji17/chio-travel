package com.chio.service

import com.chio.data.dao.TripDao
import com.chio.data.entity.Trip
import com.chio.util.QpxUtil
import com.google.api.services.qpxExpress.model.TripOption
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AirlineReservationService {

    @Autowired
    TripDao tripDao

    List<TripOption> findAirlineReservations(long tripId) {
        Trip trip = tripDao.findById(tripId)
        return QpxUtil.getFlightOptions(trip)
    }
}