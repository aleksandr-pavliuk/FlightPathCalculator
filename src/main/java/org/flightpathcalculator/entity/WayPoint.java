package org.flightpathcalculator.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Oleksandr Pavliuk
 * Represents a waypoint in the flight path of an airplane.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WayPoint {
    /**
     * Latitude of the waypoint.
     */
    private double latitude;

    /**
     * Longitude of the waypoint.
     */
    private double longitude;

    /**
     * Altitude of the waypoint in meters (m).
     */
    private double altitude;

    /**
     * Speed at which the airplane should pass the waypoint in meters per second (m/s).
     */
    private double speed;
}
