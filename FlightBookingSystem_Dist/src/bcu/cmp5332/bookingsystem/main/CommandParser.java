package bcu.cmp5332.bookingsystem.main;

import bcu.cmp5332.bookingsystem.commands.*;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;

/**
 * The CommandParser class is responsible for parsing input commands and returning the corresponding Command object.
 * 
 * @Author Subresh Thakulla / Bibek Shah
 */
public class CommandParser {

    /**
     * Parses the input command string and returns the corresponding Command object.
     * 
     * @param line The input command string.
     * @param fbs The FlightBookingSystem instance.
     * @return The corresponding Command object.
     * @throws IOException If an I/O error occurs.
     * @throws FlightBookingSystemException If an error related to the Flight Booking System occurs.
     */
    public static Command parse(String line, FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        try {
            String[] parts = line.split(" ", 3);
            String cmd = parts[0];

            if (cmd.equals("addflight")) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Flight Number: ");
                String flightNumber = reader.readLine();
                System.out.print("Origin: ");
                String origin = reader.readLine();
                System.out.print("Destination: ");
                String destination = reader.readLine();
                System.out.print("Departure Date (\"YYYY-MM-DD\" format): ");
                LocalDate departureDate = java.time.LocalDate.parse(reader.readLine());
                System.out.print("Number of Seats: ");
                int numberOfSeats = Integer.parseInt(reader.readLine());
                System.out.print("Price: ");
                int price = Integer.parseInt(reader.readLine());

                return new AddFlight(flightNumber, origin, destination, departureDate, numberOfSeats, price);

            } else if (cmd.equals("addcustomer")) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Customer Name: ");
                String name = reader.readLine();
                System.out.print("Customer Phone: ");
                String phone = reader.readLine();
                System.out.print("Customer Email: ");
                String email = reader.readLine();

                return new AddCustomer(name, phone, email);

            } else if (cmd.equals("loadgui")) {
                return new LoadGUI(fbs);  // Updated to pass FlightBookingSystem instance
            } else if (parts.length == 1) {
                if (line.equals("listflights")) {
                    return new ListFlights();
                } else if (line.equals("listcustomers")) {
                    return new ListCustomers();
                } else if (line.equals("help")) {
                    return new Help();
                }
            } else if (parts.length == 2) {
                int id = Integer.parseInt(parts[1]);

                if (cmd.equals("showflight")) {
                    return new ShowFlight(id);
                } else if (cmd.equals("showcustomer")) {
                    return new ShowCustomer(id);
                }

            } else if (cmd.equals("addbooking")) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Customer ID: ");
                int customerId = Integer.parseInt(reader.readLine());
                System.out.print("Flight ID: ");
                int flightId = Integer.parseInt(reader.readLine());

                // Check if the flight has available seats
                Flight flight = fbs.getFlightByID(flightId);
                if (flight != null && flight.getPassengers().size() < flight.getNumberOfSeats()) {
                    return new AddBooking(customerId, flightId, LocalDate.now());  // Corrected LocalDate usage
                } else {
                    System.out.println("The flight is full. Booking cannot be made.");
                    return null;
                }
            } else if (cmd.equals("editbooking")) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Enter Booking ID: ");
                int bookingId = Integer.parseInt(reader.readLine().trim());
                System.out.print("Enter New Flight ID: ");
                int newFlightId = Integer.parseInt(reader.readLine().trim());

                return new EditBooking(bookingId, newFlightId);
            } else if (cmd.equals("cancelbooking")) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Customer ID: ");
                int customerId = Integer.parseInt(reader.readLine());
                System.out.print("Flight ID: ");
                int flightId = Integer.parseInt(reader.readLine());

                return new CancelBooking(customerId, flightId);
            }
        } catch (IOException ex) {
            System.out.println("Error reading input: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Invalid input. Please enter a valid number.");
        }

        throw new FlightBookingSystemException("Invalid command.");
    }
}
