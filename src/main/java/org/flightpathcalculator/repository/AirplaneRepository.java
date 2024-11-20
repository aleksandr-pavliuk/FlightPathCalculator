package org.flightpathcalculator.repository;

import org.flightpathcalculator.entity.Airplane;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Oleksandr Pavliuk
 * Repository interface for Airplane.
 */
public interface AirplaneRepository extends MongoRepository<Airplane, Long> {
}

