package org.flightpathcalculator.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Oleksandr Pavliuk
 * Represents a temporary point that the airplane passes during the flight.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemporaryPoint {
    /**
     * Latitude of the temporary point.
     */
    private double latitude;

    /**
     * Longitude of the temporary point.
     */
    private double longitude;

    /**
     * Altitude of the temporary point in meters (m).
     */
    private double altitude;

    /**
     * Speed of the airplane at the temporary point in meters per second (m/s).
     */
    private double speed;

    /**
     * Heading of the airplane at the temporary point in degrees.
     */
    private double heading;
}
