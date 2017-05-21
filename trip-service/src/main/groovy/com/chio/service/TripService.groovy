package com.chio.service

import com.chio.data.dao.TripDao
import com.chio.data.entity.Trip
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Slf4j
public class TripService {

    @Autowired
    public TripDao tripDao;

    public Trip create(Trip trip) {
        return tripDao.create(trip);
    }

    public Trip findByIdAndUserId(long id, long userId) {
        return tripDao.findByIdAndUserId(id, userId);
    }
}
