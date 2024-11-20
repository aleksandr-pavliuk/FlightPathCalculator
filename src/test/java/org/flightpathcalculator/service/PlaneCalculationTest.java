package org.flightpathcalculator.service;

import org.flightpathcalculator.component.FlightManager;
import org.flightpathcalculator.entity.*;
import org.flightpathcalculator.repository.AirplaneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Oleksandr Pavliuk
 */
@SpringBootTest
@EnableScheduling
@ActiveProfiles("test")
public class PlaneCalculationTest {

    @Autowired
    private PlaneCalculation planeCalculation;

    @MockBean
    private AirplaneRepository airplaneRepository;

    @Autowired
    private FlightManager flightManager;

    private AirplaneCharacteristics characteristics;
    private List<WayPoint> wayPoints;
    private Airplane airplane;

    @BeforeEach
    public void setUp() {
        characteristics = new AirplaneCharacteristics(300.0, 5.0, 10.0, 15.0);
        wayPoints = new ArrayList<>();
        wayPoints.add(new WayPoint(0.0, 0.0, 1000.0, 25.0));
        wayPoints.add(new WayPoint(100.0, 100.0, 1500.0, 28.0));
        wayPoints.add(new WayPoint(200.0, 200.0, 5000.0, 30.0));
        wayPoints.add(new WayPoint(100.0, 400.0, 2000.0, 30.0));

        Flight flight = new Flight(1L, wayPoints, new ArrayList<>());
        List<Flight> flights = new ArrayList<>();
        flights.add(flight);

        airplane = new Airplane(1L, characteristics, null, flights);
    }

    @Test
    public void testCalculateRouteWithValidWaypoints() {
        List<TemporaryPoint> route = planeCalculation.calculateRoute(characteristics, wayPoints);
        assertNotNull(route);
        assertFalse(route.isEmpty());
        assertTrue(route.size() > wayPoints.size());
    }

    @Test
    public void testCalculateRouteWithEmptyWaypoints() {
        List<WayPoint> emptyWayPoints = new ArrayList<>();
        List<TemporaryPoint> route = planeCalculation.calculateRoute(characteristics, emptyWayPoints);
        assertNotNull(route);
        assertTrue(route.isEmpty());
    }

    @Test
    public void testCalculateRouteWithSingleWaypoint() {
        List<WayPoint> singleWayPoint = new ArrayList<>();
        singleWayPoint.add(new WayPoint(0.0, 0.0, 1000.0, 250.0));
        List<TemporaryPoint> route = planeCalculation.calculateRoute(characteristics, singleWayPoint);
        assertNotNull(route);
        assertTrue(route.isEmpty());
    }

    @Test
    public void testCalculatePointsBetweenWaypoints() {
        WayPoint start = new WayPoint(0.0, 0.0, 1000.0, 250.0);
        WayPoint end = new WayPoint(1.0, 1.0, 1500.0, 280.0);
        List<TemporaryPoint> points = planeCalculation.calculateRoute(characteristics, List.of(start, end));
        assertNotNull(points);
        assertFalse(points.isEmpty());
        assertEquals(start.getLatitude(), points.getFirst().getLatitude(), 0.001);
        assertEquals(start.getLongitude(), points.getFirst().getLongitude(), 0.001);
    }

    @Test
    public void testCalculateRouteSpeedLimit() {
        characteristics.setMaxSpeed(200.0);
        List<TemporaryPoint> route = planeCalculation.calculateRoute(characteristics, wayPoints);
        assertNotNull(route);
        assertFalse(route.isEmpty());
        route.forEach(point -> assertTrue(point.getSpeed() <= characteristics.getMaxSpeed()));
    }

    @Test
    public void testFlightManagerStartFlights() {
        when(airplaneRepository.findAll()).thenReturn(List.of(airplane, airplane, airplane));
        flightManager.startFlights();
        verify(airplaneRepository, times(3)).save(any(Airplane.class));
    }

}