/**
 * The FlightDataManager class is responsible for loading and storing flight data to and from a file
 * for the Flight Booking System application.
 *
 * @Author Subresh Thakulla / Bibek Shah
 */
package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * The FlightDataManager class is responsible for loading and storing flight data to and from a file
 * for the Flight Booking System application.
 */
public class FlightDataManager implements DataManager {

    /** The path to the flight data file. */
    private final String RESOURCE = "./resources/data/flights.txt";

    /** The separator used to separate fields in the data file. */
    private final String SEPARATOR = ",";

    /**
     * Loads flight data from the specified file and updates the FlightBookingSystem instance.
     *
     * @param fbs the FlightBookingSystem instance
     * @throws IOException if an I/O error occurs
     * @throws FlightBookingSystemException if an error related to the Flight Booking System occurs
     */
    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        try (Scanner sc = new Scanner(new File(RESOURCE))) {
            int line_idx = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);
                try {
                    int id = Integer.parseInt(properties[0]);
                    String flightNumber = properties[1];
                    String origin = properties[2];
                    String destination = properties[3];
                    LocalDate departureDate = LocalDate.parse(properties[4]);
                    int numberOfSeats = Integer.parseInt(properties[5]);
                    double price = Double.parseDouble(properties[6]);
                    Flight flight = new Flight(id, flightNumber, origin, destination, departureDate, numberOfSeats, price);
                    fbs.addFlight(flight);
                } catch (NumberFormatException ex) {
                    throw new FlightBookingSystemException("Unable to parse flight id " + properties[0] + " on line " + line_idx
                        + "\nError: " + ex);
                }
                line_idx++;
            }
        }
    }

    /**
     * Stores flight data to the specified file based on the FlightBookingSystem instance.
     *
     * @param fbs the FlightBookingSystem instance
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Flight flight : fbs.getFlights()) {
                out.print(flight.getId() + SEPARATOR);
                out.print(flight.getFlightNumber() + SEPARATOR);
                out.print(flight.getOrigin() + SEPARATOR);
                out.print(flight.getDestination() + SEPARATOR);
                out.print(flight.getDepartureDate() + SEPARATOR);
                out.print(flight.getNumberOfSeats() + SEPARATOR);
                out.print(flight.getPrice() + SEPARATOR);
                out.println();
            }
        }
    }

    /**
     * Deletes a flight with the specified ID from the FlightBookingSystem instance and updates the data file.
     *
     * @param flightId the ID of the flight to delete
     * @param fbs the FlightBookingSystem instance
     * @throws IOException if an I/O error occurs
     * @throws FlightBookingSystemException if an error related to the Flight Booking System occurs
     */
    public void deleteFlight(int flightId, FlightBookingSystem fbs) throws IOException, FlightBookingSystemException { 
        fbs.deleteFlight(flightId); 
        storeData(fbs); 
    } 
}
