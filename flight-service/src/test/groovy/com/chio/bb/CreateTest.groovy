package com.chio.bb

import com.chio.controller.FlightRequestController
import com.chio.data.dao.FlightRequestDao
import com.chio.data.entity.FlightDetails
import com.chio.service.FlightRequestService
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Ignore
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

@Ignore
@Slf4j
class CreateTest extends Specification {

    def jsonSlurper = new JsonSlurper()

    FlightDetails newFlightRequest
    String requestBody

    FlightRequestDao sampleDao = Mock()
    FlightRequestService sampleService = new FlightRequestService(sampleDao: sampleDao)
    FlightRequestController sampleController = new FlightRequestController(sampleService: sampleService)

    MockMvc mockMvc = standaloneSetup(sampleController).setControllerAdvice().build()

    def "Create"() {
        given:
            newFlightRequest = new FlightDetails(origin: "TPA", destination: "NYC")
            requestBody = new ObjectMapper().writeValueAsString(newFlightRequest)
            sampleDao.save(_ as FlightDetails) >> newFlightRequest
        when:
            def response = mockMvc.perform(post('/flightrequest').content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse()
            def responseBody = jsonSlurper.parse(response.getContentAsString())
            log.info(">>responseBody: ${responseBody}")
        then:
            assert response.status == HttpStatus.CREATED.value()
            assert responseBody != null
    }

    def "Read"() {
        given:
            newFlightRequest = new FlightDetails(id: 1, origin: "TPA", destination: "NYC")
            sampleDao.findById(_ as long) >> newFlightRequest
        when:
            def response = mockMvc.perform(get("/flightrequest/{id}", newFlightRequest.id))
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
    }



}
