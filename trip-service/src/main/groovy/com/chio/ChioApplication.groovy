package com.chio

import com.chio.data.entity.Trip
import com.chio.data.repository.TripRepository
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
	public CommandLineRunner demo(TripRepository repository) {
		// save a couple of customers
		repository.save(new Trip(id: 1, userId: 1, origin: "TPA", destination: "NYC"));
		repository.save(new Trip(id: 2, userId: 2, origin: "MIA", destination: "ATL"));
		repository.save(new Trip(id: 3, userId: 1, origin: "BWI", destination: "DEN"));

		// fetch all customers
		log.info("Trips found with findAll():");
		log.info("-------------------------------");
		for (Trip trip : repository.findAll()) {
			log.info(trip.toString());
		}
		log.info("");

		// fetch an individual customer by ID
		Trip trip = repository.findOne(1L);
		log.info("Trips found with findOne(1L):");
		log.info("--------------------------------");
		log.info(trip.toString());
		log.info("");

		// fetch customers by last name
		log.info("Trips found with findByIdAndUserId('1/2'):");
		log.info("--------------------------------------------");
		for (Trip userTrip : repository.findByIdAndUserId(1,2)) {
			log.info(userTrip.toString());
		}
		log.info("");

		//AirlineReservation airlineReservation = new AirlineReservation()

		//airlineReservation.getFlightOptions()
	}

}
