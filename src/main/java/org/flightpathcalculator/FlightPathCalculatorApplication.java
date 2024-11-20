package org.flightpathcalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FlightPathCalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightPathCalculatorApplication.class, args);
    }

}
