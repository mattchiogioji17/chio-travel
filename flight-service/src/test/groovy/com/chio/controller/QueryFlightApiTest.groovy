package com.chio.controller

import com.chio.data.dao.FlightRequestDao
import com.chio.model.FlightRequest
import com.chio.service.FlightRequestService
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Ignore
import spock.lang.Specification

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

@Ignore
@Slf4j
class QueryFlightApiTest extends Specification {

    def jsonSlurper = new JsonSlurper()

    FlightRequest flightRequest
    String requestBody

    FlightRequestDao flightRequestDao
    FlightRequestService flightRequestService
    FlightRequestController flightRequestController

    MockMvc mockMvc

    def setup(){
        flightRequestDao = Stub()
        //flightRequestService = new FlightRequestService(flightRequestDao: flightRequestDao)
        flightRequestService = new FlightRequestService()
        flightRequestService.flightRequestDao = flightRequestDao
        //flightRequestController = new FlightRequestController(flightRequestService: flightRequestService)
        flightRequestController = new FlightRequestController()
        flightRequestController.flightRequestService = flightRequestService
        mockMvc = standaloneSetup(flightRequestController).setControllerAdvice().build()
    }


    /*def "Happy Path: Successful request made to QPX API"() {
        given: "A POST is made to the searchFlights endpoint"
            flightRequest = new FlightRequest(origin: "TPA", destination: "NYC", date: "2017-06-01")
            flightDetails = new FlightDetails(userId: 1, tripId: 1, flightRequest: flightRequest)
        and: "The DAO finds the correct trip for the specified user"
            flightDetailsDao.findByUserIdAndTripId(_ as long, _ as long) >> flightDetails

            flightDetailsDao.save(_ as FlightDetails) >> flightDetails
        when:
            def response = mockMvc.perform(post("/user/{userId}/trip/{tripId}", flightDetails.userId, flightDetails.tripId))
                    .andReturn().getResponse()
            def responseBody = jsonSlurper.parseText(response.getContentAsString())
            log.info(">>responseBody: ${responseBody}")
        then:
            assert response.status == HttpStatus.CREATED.value()
            assert responseBody != null
    }*/

    /*def "Read"() {
        given:
        flightDetails = new FlightDetails(id: 1, origin: "TPA", destination: "NYC")
        flightDetailsDao.findById(_ as long) >> flightDetails
        when:
        def response = mockMvc.perform(get("/flightrequest/{id}", flightDetails.id))
                .andReturn().getResponse()
        def responseBody = jsonSlurper.parse(response.getContentAsString())
        log.info(">>responseBody: ${responseBody}")
        then:
        assert response.status == HttpStatus.OK.value()
        assert responseBody != null
        assert responseBody.origin == "TPA"
    }

    def "Delete"() {
        int flightRequestId = 1
        given:

        when:
        def response = mockMvc.perform(delete("/flightrequest/{id}", flightRequestId)).andReturn().getResponse()
        log.info(">>responseBody: ${response}")
        then:
        assert response.status == HttpStatus.ACCEPTED.value()
    }*/



}


