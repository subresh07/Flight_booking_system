/**
 * Command to add a new flight to the flight booking system.
 *
 * @Author Subresh Thakulla / Bibek Shah
 */
package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Command to add a new flight to the flight booking system.
 */
public class AddFlight implements Command {

    private final String flightNumber; // Flight number
    private final String origin; // Origin airport
    private final String destination; // Destination airport
    private final LocalDate departureDate; // Departure date
    private final int numberOfSeats; // Number of seats available on the flight
    private final int price; // Price of the flight

    /**
     * Constructs an AddFlight command with the specified flight details.
     *
     * @param flightNumber the flight number
     * @param origin the origin airport
     * @param destination the destination airport
     * @param departureDate the departure date of the flight
     * @param numberOfSeats the number of seats available on the flight
     * @param price the price of the flight
     */
    public AddFlight(String flightNumber, String origin, String destination, LocalDate departureDate, int numberOfSeats, int price) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.numberOfSeats = numberOfSeats;
        this.price = price;
    }

    /**
     * Executes the command to add a new flight to the flight booking system.
     *
     * @param flightBookingSystem the flight booking system
     * @throws FlightBookingSystemException if an error occurs while executing the command
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        LocalDate currentDate = LocalDate.now();
        if (departureDate.isBefore(currentDate)) {
            throw new FlightBookingSystemException("Cannot add flight with a departure date in the past.");
        }

        // Get the maximum flight ID currently in the system
        int maxId = flightBookingSystem.getFlights().stream().mapToInt(Flight::getId).max().orElse(0);

        // Create a new flight object with the next available ID
        Flight flight = new Flight(++maxId, flightNumber, origin, destination, departureDate, numberOfSeats, price);
        flightBookingSystem.addFlight(flight); // Add the flight to the flight booking system
        System.out.println("Flight #" + flight.getId() + " added.");

        // Write the new flight data to the flights.txt file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resources/data/flights.txt", true))) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = departureDate.format(dtf);
            writer.write(flight.getId() + "," + flight.getFlightNumber() + "," + flight.getOrigin() + "," + flight.getDestination() + "," + formattedDate + "," + flight.getNumberOfSeats() + "," + flight.getPrice());
            writer.newLine();
        } catch (IOException ex) {
            throw new FlightBookingSystemException("Error writing to flights.txt: " + ex.getMessage());
        }
    }
}
