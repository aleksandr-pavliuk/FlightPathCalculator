package org.flightpathcalculator.service;

import org.flightpathcalculator.entity.AirplaneCharacteristics;
import org.flightpathcalculator.entity.TemporaryPoint;
import org.flightpathcalculator.entity.WayPoint;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleksandr Pavliuk
 * Service for calculating the route of an airplane based on its characteristics and waypoints.
 */
@Service
public class PlaneCalculation {
    /**
     * Calculates the route of an airplane based on its characteristics and a list of waypoints.
     *
     * @param characteristics the characteristics of the airplane
     * @param wayPoints the list of waypoints defining the flight path
     * @return a list of temporary points representing the flight route
     */
    public List<TemporaryPoint> calculateRoute(AirplaneCharacteristics characteristics, List<WayPoint> wayPoints) {
        List<TemporaryPoint> points = new ArrayList<>();

        if (wayPoints == null || wayPoints.isEmpty()) {
            return points;
        }

        WayPoint previousWayPoint = wayPoints.getFirst();
        for (int i = 1; i < wayPoints.size(); i++) {
            WayPoint nextWayPoint = wayPoints.get(i);
            points.addAll(calculatePointsBetweenWaypoints(previousWayPoint, nextWayPoint, characteristics));
            previousWayPoint = nextWayPoint;
        }

        return points;
    }

    /**
     * Calculates the intermediate points between two waypoints.
     *
     * @param start the starting waypoint
     * @param end the ending waypoint
     * @param characteristics the characteristics of the airplane
     * @return a list of temporary points representing the path between two waypoints
     */
    private List<TemporaryPoint> calculatePointsBetweenWaypoints(WayPoint start, WayPoint end, AirplaneCharacteristics characteristics) {
        List<TemporaryPoint> points = new ArrayList<>();

        double distanceLatitude = end.getLatitude() - start.getLatitude();
        double distanceLongitude = end.getLongitude() - start.getLongitude();
        double distanceAltitude = end.getAltitude() - start.getAltitude();
        double distance = Math.sqrt(distanceLatitude * distanceLatitude + distanceLongitude * distanceLongitude);
        double speed = Math.min(start.getSpeed(), characteristics.getMaxSpeed());
        double timeToReach = distance / speed;

        double deltaLatitude = distanceLatitude / timeToReach;
        double deltaLongitude = distanceLongitude / timeToReach;
        double deltaAltitude = distanceAltitude / timeToReach;
        double heading = Math.toDegrees(Math.atan2(distanceLongitude, distanceLatitude));

        for (int t = 0; t <= timeToReach; t++) {
            double currentLatitude = start.getLatitude() + t * deltaLatitude;
            double currentLongitude = start.getLongitude() + t * deltaLongitude;
            double currentAltitude = start.getAltitude() + t * deltaAltitude;

            TemporaryPoint point = new TemporaryPoint(currentLatitude, currentLongitude, currentAltitude, speed, heading);
            points.add(point);
        }

        return points;
    }
}
