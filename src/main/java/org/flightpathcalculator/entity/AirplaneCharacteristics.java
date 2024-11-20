package org.flightpathcalculator.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Oleksandr Pavliuk
 * Represents the characteristics of an airplane.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirplaneCharacteristics {
    /**
     * Maximum speed of the airplane in meters per second (m/s).
     */
    private double maxSpeed;

    /**
     * Maximum acceleration of the airplane in meters per second squared (m/s^2).
     */
    private double acceleration;

    /**
     * Rate of climb of the airplane in meters per second (m/s).
     */
    private double climbRate;

    /**
     * Rate of turn of the airplane in degrees per second (degrees/s).
     */
    private double turnRate;
}
