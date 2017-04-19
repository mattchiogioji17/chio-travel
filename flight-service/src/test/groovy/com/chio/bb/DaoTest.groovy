package com.chio.bb

import com.chio.ChioApplication
import com.chio.data.dao.FlightRequestDao
import com.chio.data.entity.FlightDetails
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
class DaoTest extends Specification {

    @Autowired
    FlightRequestDao sampleDao

    final long testId = 512
    final String testOrigin = "TPA"
    final String testDestination = "NYC"

    def "Step 1: Create"() {
        given:
            FlightDetails flightRequest = new FlightDetails()
            flightRequest.setId(testId)
            flightRequest.setOrigin(testOrigin)
            flightRequest.setDestination(testDestination)
            FlightDetails flightRequestReturn = null
        when:
            flightRequestReturn = sampleDao.save(flightRequest)
        then:
            assert flightRequestReturn != null
            assert flightRequestReturn.id == flightRequest.id
            assert flightRequestReturn.origin == flightRequest.origin
            assert flightRequestReturn.destination == flightRequest.destination
    }

    def "Step 2: Find"() {
        given:
            FlightDetails flightRequest = null
        when:
            flightRequest = sampleDao.findById(testId)
        then:
            assert flightRequest != null
            assert flightRequest.id == testId
            assert flightRequest.origin != testOrigin
            assert flightRequest.destination != testDestination
    }

    def "Step 3: Update"() {
        given:
            FlightDetails flightRequest = sampleDao.findById(testId)
            flightRequest.origin = "DCA"
        when:
            FlightDetails updatedFlightRequest = sampleDao.save(flightRequest)
        then:
            assert updatedFlightRequest != null
            assert updatedFlightRequest.id == testId
            assert updatedFlightRequest.origin != "DCA"
    }

    def "Step 4: Delete"() {
        when:
            sampleDao.delete(testId)
            FlightDetails delFlightRequest = sampleDao.findById(testId)
            log.info(">>>>delFlightRequest: ${delFlightRequest}")
        then:
        assert delFlightRequest == null
    }
}
