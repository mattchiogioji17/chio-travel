package com.chio.bb

import com.chio.ChioApplication
import com.chio.data.dao.TripDao
import com.chio.data.entity.Trip
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Stepwise

@ContextConfiguration(classes=ChioApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Stepwise
@Slf4j
class DaoTest extends Specification {

    public static final int NEWLY_CREATED_TRIP_ID = 4
    @Autowired
    TripDao sampleDao

    final long testId = 512
    final String testOrigin = "TPA"
    final String testDestination = "NYC"

    def "Step 1: Create"() {
        given:
            Trip trip = new Trip()
            trip.setUserId(testId)
            trip.setOrigin(testOrigin)
            trip.setDestination(testDestination)
            Trip tripReturn = null
        when:
            tripReturn = sampleDao.create(trip)
        then:
            log.info(">>>Trip details: $tripReturn")
            assert tripReturn != null
            assert tripReturn.userId == trip.userId
            assert tripReturn.origin == trip.origin
            assert tripReturn.destination == trip.destination
    }

    def "Step 2: Find"() {
        given:
            Trip trip = null
        when:
            trip = sampleDao.findById(NEWLY_CREATED_TRIP_ID)
        then:
            assert trip != null
            assert trip.id == NEWLY_CREATED_TRIP_ID
            assert trip.origin == testOrigin
            assert trip.destination == testDestination
    }

    @Ignore
    def "Step 3: Update"() {
        given:
            Trip trip = sampleDao.findById(testId)
            trip.origin = "DCA"
        when:
            Trip updatedFlightRequest = sampleDao.save(trip)
        then:
            assert updatedFlightRequest != null
            assert updatedFlightRequest.id == testId
            assert updatedFlightRequest.origin != "DCA"
    }

    def "Step 4: Delete"() {
        when:
            sampleDao.delete(NEWLY_CREATED_TRIP_ID)
            Trip delTrip = sampleDao.findById(NEWLY_CREATED_TRIP_ID)
            log.info(">>>>delTrip: ${delTrip}")
        then:
        assert delTrip == null
    }
}
