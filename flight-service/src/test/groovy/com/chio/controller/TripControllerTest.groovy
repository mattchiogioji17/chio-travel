package com.chio.controller

import com.chio.data.dao.FlightRequestDao
import com.chio.model.FlightRequest
import com.chio.service.FlightRequestService
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

@Slf4j
class FlightRequestControllerTest extends Specification {

    def jsonSlurper = new JsonSlurper()

    FlightRequest flightRequest
    String requestBody

    FlightRequestDao flightRequestDao
    FlightRequestService flightRequestService
    FlightRequestController flightRequestController

    MockMvc mockMvc

    def setup(){
        flightRequestDao = Mock(FlightRequestDao)
        flightRequestService = new FlightRequestService(flightRequestDao: flightRequestDao)
        flightRequestController = new FlightRequestController(flightRequestService: flightRequestService)
        mockMvc = standaloneSetup(flightRequestController).setControllerAdvice().build()
    }

    def "Happy Path: Flight Request created"() {
        given: "A POST is made to the createFlightRequest endpoint"
            flightRequest = new FlightRequest(userId: 1, tripId: 1, origin: "TPA", destination: "NYC", date: "2017-06-01")
            requestBody = new ObjectMapper().writeValueAsString(flightRequest)
            flightRequestDao.create(_ as FlightRequest) >> flightRequest
        when:
            def response = mockMvc.perform(post("/flight-request").content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse()
            log.info(">>response: ${response.getContentAsString()}")
            def responseBody = jsonSlurper.parseText(response.getContentAsString())
            log.info(">>responseBody: ${responseBody}")
        then:
            assert response.status == HttpStatus.CREATED.value()
            assert responseBody != null
            assert responseBody.origin == "TPA"
    }

    def "Happy Path: Get Flight Request by UserId and TripId"() {
        given:
            flightRequest = new FlightRequest(userId: 1, tripId: 1, origin: "TPA", destination: "NYC")
            flightRequestDao.findByUserIdAndTripId(_ as String, _ as String) >> flightRequest
        when:
            def response = mockMvc.perform(get("/flight-request/user/{userId}/trip/{tripId}",
                    flightRequest.userId, flightRequest.tripId)).andReturn().getResponse()
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


