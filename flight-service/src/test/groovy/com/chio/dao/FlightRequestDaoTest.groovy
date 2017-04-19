package com.chio.dao

import com.chio.ChioApplication
import com.chio.data.dao.FlightRequestDao
import com.chio.model.FlightRequest
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Stepwise

@Ignore
@ContextConfiguration(classes=ChioApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Stepwise
@Slf4j
class FlightRequestDaoTest extends Specification {

    @Autowired
    FlightRequestDao flightRequestDao

    final long testId = 512
    final String testOrigin = "TPA"
    final String testDestination = "NYC"

    def "Step 1: Create"() {
        given:
            FlightRequest flightRequest = new FlightRequest()
            flightRequest.setId(testId)
            flightRequest.setOrigin(testOrigin)
            flightRequest.setDestination(testDestination)
            FlightRequest flightRequestReturn = null
        when:
            flightRequestReturn = flightRequestDao.create(flightRequest)
        then:
            assert flightRequestReturn != null
            assert flightRequestReturn.id == flightRequest.id
            assert flightRequestReturn.origin == flightRequest.origin
            assert flightRequestReturn.destination == flightRequest.destination
    }

    def "Step 2: Find"() {
        given:
            FlightRequest flightRequest = null
        when:
            flightRequest = flightRequestDao.findById(testId)
        then:
            assert flightRequest != null
            assert flightRequest.id == testId
            assert flightRequest.origin != testOrigin
            assert flightRequest.destination != testDestination
    }

    def "Step 3: Update"() {
        given:
            FlightRequest flightRequest = flightRequestDao.findById(testId)
            flightRequest.origin = "DCA"
        when:
            FlightRequest updatedFlightRequest = flightRequestDao.save(flightRequest)
        then:
            assert updatedFlightRequest != null
            assert updatedFlightRequest.id == testId
            assert updatedFlightRequest.origin != "DCA"
    }

    def "Step 4: Delete"() {
        when:
            flightRequestDao.delete(testId)
            FlightRequest delFlightRequest = flightRequestDao.findById(testId)
            log.info(">>>>delFlightRequest: ${delFlightRequest}")
        then:
        assert delFlightRequest == null
    }
}
