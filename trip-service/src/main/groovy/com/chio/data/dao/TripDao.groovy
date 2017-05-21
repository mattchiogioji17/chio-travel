package com.chio.data.dao

import com.chio.data.entity.Trip
import com.chio.data.repository.TripRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@Slf4j
public class TripDao {

    @Autowired
    private TripRepository tripRepository;

    public Trip create(Trip trip) {
        log.info("inside dao: ${trip}");
        return tripRepository.save(trip);
    }

    public Trip findById(long id) {
        return tripRepository.findById(id);
    }

    public Trip findByIdAndUserId(long id, long userId) {
        log.info("inside dao: ${id}");

        return tripRepository.findByIdAndUserId(id, userId)
    }

    void delete(long id) {
        tripRepository.delete(id)
    }
}
