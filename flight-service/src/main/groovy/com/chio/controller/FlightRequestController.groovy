package com.chio.controller;

import com.chio.model.FlightRequest;
import com.chio.service.FlightRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@Slf4j
@RequestMapping(value="/flight-request", produces = "application/json")
public class FlightRequestController {

    @Autowired
    public FlightRequestService flightRequestService;

    public FlightRequestController(){}

    @PostMapping
    @ResponseBody
    public ResponseEntity<FlightRequest> create(@RequestBody FlightRequest flightRequest) {
        FlightRequest newFlightRequest = flightRequestService.create(flightRequest);
        return new ResponseEntity<FlightRequest>(newFlightRequest, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/trip/{tripId}")
    public ResponseEntity<FlightRequest> findByUserIdAndTripId(
            @PathVariable String userId, @PathVariable String tripId) {
        //log.info("in controller: user: ${userId}")
        FlightRequest flightRequest = flightRequestService.findByUserIdAndTripId(userId, tripId);
        return new ResponseEntity<FlightRequest>(flightRequest, HttpStatus.OK);

    }


}
