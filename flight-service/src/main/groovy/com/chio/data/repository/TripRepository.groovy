package com.chio.data.repository;

import com.chio.model.FlightRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRequestRepository extends CrudRepository<FlightRequest, Long> {

    FlightRequest findById(long id);

    FlightRequest findByUserIdAndTripId(String userId, String tripId);
}
