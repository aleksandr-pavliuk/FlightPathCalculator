package org.flightpathcalculator.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author Oleksandr Pavliuk
 * Represents an airplane with specific characteristics, current position, and flights.
 */
@Document(collection = "airplanes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Airplane {
    /**
     * Unique identifier for the airplane.
     */
    @Id
    private Long id;

    /**
     * Characteristics of the airplane, such as speed and acceleration.
     */
    private AirplaneCharacteristics characteristics;

    /**
     * Current position of the airplane.
     */
    private TemporaryPoint position;

    /**
     * List of flights associated with the airplane.
     */
    private List<Flight> flights;
}
