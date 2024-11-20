package org.flightpathcalculator.component;

import lombok.extern.slf4j.Slf4j;
import org.flightpathcalculator.entity.*;
import org.flightpathcalculator.repository.AirplaneRepository;
import org.flightpathcalculator.service.PlaneCalculation;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Oleksandr Pavliuk
 * Component responsible for managing airplane flights.
 */
@Slf4j
@Component
public class FlightManager {

    private final AirplaneRepository airplaneRepository;

    private final PlaneCalculation planeCalculation;

    private final List<FlightExecutor> activeFlightExecutors = new ArrayList<>();


    public FlightManager(AirplaneRepository airplaneRepository, PlaneCalculation planeCalculation) {
        this.airplaneRepository = airplaneRepository;
        this.planeCalculation = planeCalculation;
    }

    /**
     * Schedules the flights for three airplanes.
     */
    @Scheduled(fixedRate = 1000)
    public void startFlights() {
        if (activeFlightExecutors.isEmpty()) {
            List<Airplane> airplanes = airplaneRepository.findAll();
            if (airplanes.size() < 3) {
                log.info("Not enough airplanes available to start flights.");
                return;
            }

            for (int i = 0; i < 3; i++) {
                Airplane airplane = airplanes.get(i);
                List<Flight> flights = airplane.getFlights();
                log.info("Flights : {}", flights.size());
                flights.forEach(flight ->log.info("Passed time : {} seconds",flight.getPassedPoints().size()) );
                AirplaneCharacteristics characteristics = airplane.getCharacteristics();
                List<WayPoint> wayPoints = createWayPointsForFlight(i);
                Flight flight = new Flight((long) (i + 1), wayPoints, new ArrayList<>());
                List<TemporaryPoint> route = planeCalculation.calculateRoute(characteristics, wayPoints);
                activeFlightExecutors.add(new FlightExecutor(airplane, flight, route));
            }
        }

        activeFlightExecutors.forEach(FlightExecutor::executeNextPoint);
        activeFlightExecutors.removeIf(FlightExecutor::isFlightCompleted);
    }

    private List<WayPoint> createWayPointsForFlight(int index) {
        List<WayPoint> wayPoints = new ArrayList<>();
        switch (index) {
            case 0 -> {
                wayPoints.add(new WayPoint(0.0, 0.0, 1000.0, 25.0));
                wayPoints.add(new WayPoint(100.0, 100.0, 1500.0, 28.0));
                wayPoints.add(new WayPoint(200.0, 200.0, 5000.0, 30.0));
                wayPoints.add(new WayPoint(100.0, 400.0, 2000.0, 30.0));
            }
            case 1 -> {
                wayPoints.add(new WayPoint(0.0, 0.0, 1000.0, 25.0));
                wayPoints.add(new WayPoint(300.0, 100.0, 1500.0, 28.0));
                wayPoints.add(new WayPoint(200.0, 500.0, 7000.0, 30.0));
                wayPoints.add(new WayPoint(100.0, 400.0, 2000.0, 30.0));
            }
            case 2 -> {
                wayPoints.add(new WayPoint(0.0, 0.0, 1000.0, 25.0));
                wayPoints.add(new WayPoint(300.0, 500.0, 1500.0, 28.0));
                wayPoints.add(new WayPoint(700.0, 200.0, 3000.0, 30.0));
                wayPoints.add(new WayPoint(100.0, 400.0, 7000.0, 30.0));
            }
            default -> throw new IllegalArgumentException("Invalid flight index: " + index);
        }
        return wayPoints;
    }

    private class FlightExecutor {
        private final Airplane airplane;
        private final Flight flight;
        private final List<TemporaryPoint> route;
        private final AtomicInteger currentIndex = new AtomicInteger(0);

        public FlightExecutor(Airplane airplane, Flight flight, List<TemporaryPoint> route) {
            this.airplane = airplane;
            this.flight = flight;
            this.route = route;
        }

        public void executeNextPoint() {
            int index = currentIndex.getAndIncrement();
            if (index < route.size()) {
                TemporaryPoint nextPoint = route.get(index);
                airplane.setPosition(nextPoint);
                flight.getPassedPoints().add(nextPoint);
                airplaneRepository.save(airplane);
                log.info("Airplane {} updated to point {} {}", airplane.getId(), index, nextPoint.toString());
            }
        }

        public boolean isFlightCompleted() {
            if (currentIndex.get() >= route.size()) {
                airplane.getFlights().add(flight);
                airplaneRepository.save(airplane);
                log.info("Flight for airplane {} completed.", airplane.getId());
                return true;
            }
            return false;
        }
    }
}
