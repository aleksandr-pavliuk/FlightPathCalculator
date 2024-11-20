package org.flightpathcalculator.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Oleksandr Pavliuk
 * Represents a flight that an airplane takes, including waypoints and passed points.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    /**
     * Unique number identifying the flight.
     */
    private Long number;

    /**
     * List of waypoints that define the flight path.
     */
    private List<WayPoint> wayPoints;

    /**
     * List of temporary points that the airplane has passed during the flight.
     */
    private List<TemporaryPoint> passedPoints;
}
