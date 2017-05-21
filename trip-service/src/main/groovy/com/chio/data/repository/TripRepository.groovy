package com.chio.data.repository

import com.chio.data.entity.Trip
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
public interface TripRepository extends CrudRepository<Trip, Long> {

    Trip findById(long id);

    Trip findByIdAndUserId(long userId, long id);
}
