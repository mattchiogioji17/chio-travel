package com.chio.controller

import com.chio.data.dao.TripDao
import com.chio.data.entity.Trip
import com.chio.service.TripService
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

@Slf4j
class TripControllerTest extends Specification {

    public static final String TRIP_SERVICE_BASE_URL = "/trip-service"
    def jsonSlurper = new JsonSlurper()

    Trip trip
    String requestBody

    TripDao tripDao
    TripService tripService
    TripController tripController

    MockMvc mockMvc

    def setup(){
        tripDao = Mock(TripDao)
        tripService = new TripService(tripDao: tripDao)
        tripController = new TripController(tripService: tripService)
        mockMvc = standaloneSetup(tripController).setControllerAdvice().build()
    }

    def "Happy Path: Trip created"() {
        given: "A POST is made to the createTrip endpoint"
            trip = new Trip(userId: 1, origin: "TPA", destination: "NYC", date: "2017-06-01")
            requestBody = new ObjectMapper().writeValueAsString(trip)
            tripDao.create(_ as Trip) >> trip
        when:
            def response = mockMvc.perform(post(TRIP_SERVICE_BASE_URL).content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse()
            log.info(">>response: ${response.getContentAsString()}")
            def responseBody = jsonSlurper.parseText(response.getContentAsString())
            log.info(">>responseBody: ${responseBody}")
        then:
            assert response.status == HttpStatus.CREATED.value()
            assert responseBody != null
            assert responseBody.origin == "TPA"
    }

    def "Happy Path: Get Trip by UserId and TripId"() {
        given:
            trip = new Trip(id: 1, userId: 1, origin: "TPA", destination: "NYC")
            tripDao.findByIdAndUserId(_, _) >> trip
        when:
            def response = mockMvc.perform(get("$TRIP_SERVICE_BASE_URL/user/{userId}/trip/{id}",
                    1, 1)).andReturn().getResponse()
            log.info(">>response: ${response.getContentAsString()}")

            def responseBody = jsonSlurper.parseText(response.getContentAsString())
            log.info(">>responseBody: ${responseBody}")
        then:
            assert response.status == HttpStatus.OK.value()
            assert responseBody != null
            assert responseBody.origin == "TPA"
    }

    /*def "Delete"() {
        int flightRequestId = 1
        given:

        when:
        def response = mockMvc.perform(delete("/flightrequest/{id}", flightRequestId)).andReturn().getResponse()
        log.info(">>responseBody: ${response}")
        then:
        assert response.status == HttpStatus.ACCEPTED.value()
    }*/



}


