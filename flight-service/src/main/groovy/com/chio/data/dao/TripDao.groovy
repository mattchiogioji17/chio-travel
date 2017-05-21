package com.chio.data.dao

import com.chio.data.repository.FlightRequestRepository
import com.chio.model.FlightRequest
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@Slf4j
public class FlightRequestDao {

    @Autowired
    private FlightRequestRepository flightRequestRepository;

    public FlightRequest create(FlightRequest flightRequest) {
        log.info("inside dao: ${flightRequest}");
        return flightRequestRepository.save(flightRequest);
    }

    public FlightRequest findById(long id) {
        return flightRequestRepository.findById(id);
    }

    public FlightRequest findByUserIdAndTripId(String userId, String tripId) {
        log.info("inside dao: ${userId}");

        return flightRequestRepository.findByUserIdAndTripId(userId, tripId);
    }

    void delete(long id) {
        flightRequestRepository.delete(id)
    }
}
