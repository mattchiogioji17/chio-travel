package com.chio.bb

import com.chio.controller.TripController
import com.chio.data.dao.TripDao
import com.chio.data.entity.Trip
import com.chio.service.TripService
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

    Trip trip
    String requestBody

    TripDao sampleDao = Mock()
    TripService sampleService = new TripService(sampleDao: sampleDao)
    TripController sampleController = new TripController(sampleService: sampleService)

    MockMvc mockMvc = standaloneSetup(sampleController).setControllerAdvice().build()

    def "Create"() {
        given:
            trip = new Trip(origin: "TPA", destination: "NYC")
            requestBody = new ObjectMapper().writeValueAsString(trip)
            sampleDao.save(_ as Trip) >> trip
        when:
            def response = mockMvc.perform(post('/trip').content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse()
            def responseBody = jsonSlurper.parse(response.getContentAsString())
            log.info(">>responseBody: ${responseBody}")
        then:
            assert response.status == HttpStatus.CREATED.value()
            assert responseBody != null
    }

    def "Read"() {
        given:
            trip = new Trip(id: 1, origin: "TPA", destination: "NYC")
            sampleDao.findById(_ as long) >> trip
        when:
            def response = mockMvc.perform(get("/trip/{id}", trip.id))
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
            def response = mockMvc.perform(delete("/trip/{id}", flightRequestId)).andReturn().getResponse()
            log.info(">>responseBody: ${response}")
        then:
            assert response.status == HttpStatus.ACCEPTED.value()
    }



}
