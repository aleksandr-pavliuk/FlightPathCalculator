## Project Overview

This project is a Spring Boot application designed to simulate airplane flights using calculated routes. The application uses MongoDB for data persistence and Docker Compose to run MongoDB in a containerized environment. The core functionality involves managing airplane characteristics, calculating flight routes, and executing flights for multiple airplanes.

### Technologies Used

Java 21: The application is built using Java 21.

Spring Boot 3: For easy application setup and development.

Spring Data MongoDB: Used for database access and interaction with MongoDB.

MongoDB: Stores airplane data and flight information.

Docker Compose: Sets up MongoDB as a service for the Spring Boot application.

Maven: Build tool to manage dependencies and package the application.

## Running the Application

Follow these steps to run the application:

Prerequisites

Ensure that you have the following software installed on your machine:

Java 21: The application is built using Java 21, so it needs to be installed.

Docker and Docker Compose: Used to set up MongoDB.

Maven: To build and run the Spring Boot application.

## Step 1: Clone the Repository

Clone the project repository to your local machine:

`git clone <repository-url>`

## Step 2: Start MongoDB Using Docker Compose

Start MongoDB by running the following command:

`docker-compose up -d`

This command will start MongoDB in a Docker container and expose it on port 27017.

## Step 3: Configure Spring Boot Application Properties

Update the src/main/resources/application.properties file to configure the connection to MongoDB:

`spring.data.mongodb.uri=mongodb://root:example@localhost:27017/`
`spring.data.mongodb.database=airplane_db`
`spring.data.mongodb.authentication-database=admin`

## Step 4: Build the Application

Navigate to the root directory of the project and build the application using Maven:

`mvn clean install`

## Step 5: Run the Application

Run the Spring Boot application using the following command:

`mvn spring-boot:run`

Alternatively, you can run the packaged JAR file:

`java -jar target/your-application.jar`

The application will start and connect to the MongoDB instance running in the Docker container.

Application Features

Flight Simulation: The application manages three airplanes and simulates their flights using a series of waypoints.

Automatic Flight Scheduling: Flights are automatically scheduled and executed every second, with airplane positions updated in MongoDB.

Flight Data Logging: The application logs the progress of each flight, including completed flights and airplane positions.

## Testing

The project includes unit tests for core functionalities:

PlaneCalculationTests: Validates route calculation and ensures flights are executed as expected.

To run the tests, use:

`mvn test`

## Summary

The project simulates airplane routes and stores their data in MongoDB. With Docker Compose managing MongoDB and Spring Boot providing an easy development framework, this project offers a complete solution for flight simulation with route calculation and logging.
