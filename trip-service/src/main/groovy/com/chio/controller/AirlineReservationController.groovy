package com.chio.controller

import com.chio.service.AirlineReservationService
import com.google.api.services.qpxExpress.model.TripOption
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@Slf4j
@RequestMapping(value="/trip-service/airline-reservations", produces = "application/json")
class AirlineReservationController {

    @Autowired
    AirlineReservationService airlineReservationService

    @PostMapping("/{tripId}")
    @ResponseBody
    ResponseEntity<List<TripOption>> findAirlineReservations(@PathVariable long tripId) {
        List<TripOption> tripOptions = airlineReservationService.findAirlineReservations(tripId)
        return new ResponseEntity<List<TripOption>>(tripOptions, HttpStatus.OK)
    }
}
