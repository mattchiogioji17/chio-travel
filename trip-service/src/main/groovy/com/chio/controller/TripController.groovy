package com.chio.controller

import com.chio.data.entity.Trip
import com.chio.service.TripService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@Slf4j
@RequestMapping(value="/trip", produces = "application/json")
class TripController {

    @Autowired
    TripService tripService

    TripController(){}

    @GetMapping
    String helloWorld() {
        return "Hello Docker World!"
    }

    @PostMapping
    @ResponseBody
    ResponseEntity<Trip> create(@RequestBody Trip trip) {
        Trip newTrip = tripService.create(trip)
        return new ResponseEntity<Trip>(newTrip, HttpStatus.CREATED)
    }

    @GetMapping("/user/{userId}/trip/{id}")
    ResponseEntity<Trip> findByUserIdAndTripId(
            @PathVariable long userId, @PathVariable long id) {
        log.info("in controller: id: ${id}")
        log.info("in controller: user: ${userId}")
        Trip trip = tripService.findByIdAndUserId(id, userId)
        return new ResponseEntity<Trip>(trip, HttpStatus.OK)
    }


}
