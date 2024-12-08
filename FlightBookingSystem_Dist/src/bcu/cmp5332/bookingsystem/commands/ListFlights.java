/**
 * Command to list all upcoming flights in the flight booking system.
 *
 * @Author Subresh Thakulla / Bibek Shah
 */
package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Command to list all upcoming flights in the flight booking system.
 */
public class ListFlights implements Command {

    /**
     * Executes the command to list all upcoming flights in the flight booking system.
     *
     * @param flightBookingSystem the flight booking system
     * @throws FlightBookingSystemException if an error occurs while executing the command
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        List<Flight> flights = readFlightsFromFile("resources/data/flights.txt");
        LocalDate today = LocalDate.now();
        flights = filterFlights(flights, today);
        for (Flight flight : flights) {
            System.out.println(flight.getDetailsShort());
        }
        System.out.println(flights.size() + " flight(s)");
    }

    /**
     * Reads the list of flights from the specified file.
     *
     * @param filename the name of the file containing flight data
     * @return a list of flights
     * @throws FlightBookingSystemException if an error occurs while reading the file
     */
    private List<Flight> readFlightsFromFile(String filename) throws FlightBookingSystemException {
        List<Flight> flights = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String flightNumber = parts[1];
                String origin = parts[2];
                String destination = parts[3];
                LocalDate departureDate = LocalDate.parse(parts[4]);
                int numberOfSeats = Integer.parseInt(parts[5]);
                double price = Double.parseDouble(parts[6]);

                Flight flight = new Flight(id, flightNumber, origin, destination, departureDate, numberOfSeats, price);
                flights.add(flight);
            }
        } catch (IOException | NumberFormatException e) {
            throw new FlightBookingSystemException("Error reading flights from file: " + e.getMessage());
        }
        return flights;
    }

    /**
     * Filters the list of flights to include only those that depart after the specified date.
     *
     * @param flights the list of flights to filter
     * @param today the date to compare against
     * @return a list of flights that depart after the specified date
     */
    private List<Flight> filterFlights(List<Flight> flights, LocalDate today) {
        return flights.stream()
                .filter(flight -> flight.getDepartureDate().isAfter(today))
                .collect(Collectors.toList());
    }
}
