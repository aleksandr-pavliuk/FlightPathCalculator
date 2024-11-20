package org.flightpathcalculator.component;

import org.flightpathcalculator.entity.Airplane;
import org.flightpathcalculator.entity.AirplaneCharacteristics;
import org.flightpathcalculator.entity.Flight;
import org.flightpathcalculator.entity.WayPoint;
import org.flightpathcalculator.repository.AirplaneRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleksandr Pavliuk
 * Component for initializing the database with test airplanes.
 */
@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final AirplaneRepository airplaneRepository;

    public DatabaseInitializer(AirplaneRepository airplaneRepository) {
        this.airplaneRepository = airplaneRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Airplane> airplanes = new ArrayList<>();

        AirplaneCharacteristics characteristics = new AirplaneCharacteristics(15.0, 5.0, 10.0, 15.0);
        List<WayPoint> wayPoints = new ArrayList<>();
        Flight flight = new Flight(1L, wayPoints, new ArrayList<>());
        List<Flight> flights = new ArrayList<>();
        flights.add(flight);
        Airplane airplane = new Airplane(1L, characteristics, null, flights);
        airplanes.add(airplane);
        characteristics = new AirplaneCharacteristics(30.0, 5.0, 10.0, 20.0);
        wayPoints = new ArrayList<>();
        flight = new Flight(2L, wayPoints, new ArrayList<>());
        flights = new ArrayList<>();
        flights.add(flight);
        airplane = new Airplane(2L, characteristics, null, flights);
        airplanes.add(airplane);

        characteristics = new AirplaneCharacteristics(30.0, 3.0, 15.0, 10.0);
        wayPoints = new ArrayList<>();
        flight = new Flight(3L, wayPoints, new ArrayList<>());
        flights = new ArrayList<>();
        flights.add(flight);
        airplane = new Airplane(3L, characteristics, null, flights);
        airplanes.add(airplane);

        airplaneRepository.saveAll(airplanes);
    }
}
