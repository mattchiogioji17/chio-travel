package com.chio.controller

import com.chio.data.dao.TripDao
import com.chio.data.entity.Trip
import com.chio.service.TripService
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

    Trip tripRequest
    String requestBody

    TripDao tripDao
    TripService tripService
    TripController tripController

    MockMvc mockMvc

    def setup(){
        tripDao = Stub()
        //tripService = new FlightRequestService(tripDao: tripDao)
        tripService = new TripService()
        tripService.tripDao = tripDao
        //tripController = new FlightRequestController(tripService: tripService)
        tripController = new TripController()
        tripController.tripService = tripService
        mockMvc = standaloneSetup(tripController).setControllerAdvice().build()
    }


    /*def "Happy Path: Successful request made to QPX API"() {
        given: "A POST is made to the searchFlights endpoint"
            trip = new FlightRequest(origin: "TPA", destination: "NYC", date: "2017-06-01")
            flightDetails = new FlightDetails(userId: 1, tripId: 1, trip: trip)
        and: "The DAO finds the correct trip for the specified user"
            flightDetailsDao.findByIdAndUserId(_ as long, _ as long) >> flightDetails

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


