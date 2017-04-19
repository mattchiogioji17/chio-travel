package com.chio

import com.chio.data.repository.FlightRequestRepository
import com.chio.model.FlightRequest
import groovy.util.logging.Slf4j
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication
@Slf4j
public class ChioApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChioApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public CommandLineRunner demo(FlightRequestRepository repository) {
		// save a couple of customers
		repository.save(new FlightRequest(userId: 1, tripId: 1, origin: "TPA", destination: "NYC"));
		repository.save(new FlightRequest(userId: 1, tripId: 2, origin: "MIA", destination: "ATL"));
		repository.save(new FlightRequest(userId: 2, tripId: 1, origin: "BWI", destination: "DEN"));

		// fetch all customers
		log.info("FlightRequests found with findAll():");
		log.info("-------------------------------");
		for (FlightRequest flightRequest : repository.findAll()) {
			log.info(flightRequest.toString());
		}
		log.info("");

		// fetch an individual customer by ID
		FlightRequest flightRequest = repository.findOne(1L);
		log.info("FlightRequest found with findOne(1L):");
		log.info("--------------------------------");
		log.info(flightRequest.toString());
		log.info("");

		// fetch customers by last name
		log.info("FlightRequest found with findByUserIdAndTripId('1/2'):");
		log.info("--------------------------------------------");
		for (FlightRequest userTrip : repository.findByUserIdAndTripId("1","2")) {
			log.info(userTrip.toString());
		}
		log.info("");
	}

}
