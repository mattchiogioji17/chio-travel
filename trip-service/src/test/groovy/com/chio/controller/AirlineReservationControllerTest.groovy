package com.chio.controller

import com.chio.data.dao.TripDao
import com.chio.data.entity.Trip
import com.chio.service.AirlineReservationService
import com.chio.util.QpxUtil
import com.google.api.services.qpxExpress.model.TripOption
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Ignore
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

@Ignore
@Slf4j
class AirlineReservationControllerTest extends Specification {

    public static final String TRIP_SERVICE_BASE_URL = "/trip-service"
    def jsonSlurper = new JsonSlurper()

    Trip trip

    TripDao tripDao
    AirlineReservationService airlineReservationService
    AirlineReservationController airlineReservationController

    MockMvc mockMvc

    def setup(){
        tripDao = Mock(TripDao)
        airlineReservationService = new AirlineReservationService(tripDao: tripDao)
        airlineReservationController = new AirlineReservationController(airlineReservationService: airlineReservationService)
        mockMvc = standaloneSetup(airlineReservationController).setControllerAdvice().build()
    }

    def "Happy Path: Airline Reservations found for Trip"() {
        given: "A Trip exists for the Id that is requested"
            trip = new Trip(userId: 1, origin: "TPA", destination: "NYC", date: "2017-06-01")
            tripDao.findById(_) >> trip
            QpxUtil.getFlightOptions(_) >> new ArrayList<TripOption>()
        when: "A POST is made to the findAirlineReservations endpoint for a Trip ID"
            def response = mockMvc.perform(post("$TRIP_SERVICE_BASE_URL/airline-reservations/{tripId}", 1).content()
                    .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse()
            log.info(">>response: ${response.getContentAsString()}")
            def responseBody = jsonSlurper.parseText(response.getContentAsString())
            log.info(">>responseBody: ${responseBody}")
        then: "AirlineReservations are successfully retrieved"
            assert responseBody != null
            assert response.status == HttpStatus.OK.value()
    }
}
